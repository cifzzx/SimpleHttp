package com.cifz.simplehttp.simple;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Dispatcher {
    //同时访问任务  最大限制64个
    private int maxRequest = 64;
    // 同时访问同一个服务器域名，最大限制5个
    private int maxRequestsPerHost = 5;

    private Deque<Realcall.AsyncCall> runningAsyncCalls = new ArrayDeque<>();
    private Deque<Realcall.AsyncCall> readyAsyncCalls = new ArrayDeque<>();

    public void enqueue(Realcall.AsyncCall call){
        if(runningAsyncCalls.size() < maxRequest && runningCallsForHost(call) < maxRequestsPerHost){
            runningAsyncCalls.add(call);
            executorSevice().execute(call);
        }else {
            readyAsyncCalls.add(call);
        }
    }

    private ExecutorService executorSevice() {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("simpleOkhttp");
                thread.setDaemon(false);
                return thread;
            }
        });
        return executorService;
    }

    private int runningCallsForHost(Realcall.AsyncCall call) {
        int count = 0;
        if(runningAsyncCalls.isEmpty()){
            return 0;
        }
        SocketRequestServer srs = new SocketRequestServer();
        for (Realcall.AsyncCall runningAsyncCall : runningAsyncCalls) {
            if(srs.getHost(runningAsyncCall.getRequest()).equals(call.getRequest())){
                count ++;
            }
        }
        return count;
    }


    public void finish(Realcall.AsyncCall call) {
        runningAsyncCalls.remove(call);

        if(readyAsyncCalls.isEmpty()){
            return;
        }

        for (Realcall.AsyncCall readyAsyncCall : readyAsyncCalls) {
            readyAsyncCalls.remove(readyAsyncCall);
            runningAsyncCalls.add(readyAsyncCall);
            executorSevice().execute(readyAsyncCall);
        }
    }
}
