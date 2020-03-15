package com.cifz.simplehttp.simple;

import android.util.Log;

import androidx.appcompat.widget.DialogTitle;

import com.cifz.simplehttp.simple.chain.ChainManager;
import com.cifz.simplehttp.simple.chain.ConnectionServerInterceptor;
import com.cifz.simplehttp.simple.chain.Interceptor;
import com.cifz.simplehttp.simple.chain.ReRequestInterceptor;
import com.cifz.simplehttp.simple.chain.RequestHeaderInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Realcall implements Call{
    private static final String TAG = "MainActivityTag";
    private OkHttpClient okHttpClient;
    private Request request;

    public Realcall(OkHttpClient okHttpClient, Request request) {
        this.okHttpClient = okHttpClient;
        this.request = request;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private boolean executed;
    @Override
    public void enqueue(Callback responseCallback) {
        synchronized (this){
            if(executed){
                executed = true;
                throw new IllegalStateException("不能被重复的执行 enqueue Already Executed");
            }
        }
        okHttpClient.getDispatcher().enqueue(new AsyncCall(responseCallback));
    }

    final class AsyncCall implements Runnable{

        public Request getRequest(){
            return Realcall.this.request;
        }

        private Callback callback;

        private AsyncCall(Callback callback){
            this.callback = callback;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response response = getResponseWithInterceptorChain();
                //如果用户取消了请求回调给用户说失败了
                if(okHttpClient.getCanceled()){
                    signalledCallback = true;
                    callback.onFailure(Realcall.this,new IOException("用户取消了 Canceled"));
                }else {
                    signalledCallback = true;
                    callback.onResponse(Realcall.this,response);
                }
            }catch (Exception e){
                if(signalledCallback){
                    System.out.println("用户再使用过程中 出错了...");
                    Log.e(TAG,"用户再使用过程中 出错了..." + e.getMessage());
                }else {
                    callback.onFailure(Realcall.this,new IOException("OKHTTP getResponseWithInterceptorChain 错误... e:" + e.toString()));
                }
            }finally {
                //回收
                okHttpClient.getDispatcher().finish(this);
            }
        }
    }

    private Response getResponseWithInterceptorChain() throws IOException {

        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ReRequestInterceptor());
        interceptors.add(new RequestHeaderInterceptor());
        interceptors.add(new ConnectionServerInterceptor());

        ChainManager chainManager = new ChainManager(interceptors,0,Realcall.this,request);

        return chainManager.getResponse(request);
    }

}
