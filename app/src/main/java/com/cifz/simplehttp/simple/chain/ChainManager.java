package com.cifz.simplehttp.simple.chain;

import com.cifz.simplehttp.simple.Realcall;
import com.cifz.simplehttp.simple.Request;
import com.cifz.simplehttp.simple.Response;

import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ChainManager implements Chain {

    private final List<Interceptor> interceptors;
    private int index;
    private final Realcall realcall;
    private final Request request;

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public int getIndex() {
        return index;
    }

    public Realcall getRealcall() {
        return realcall;
    }

    public ChainManager(List<Interceptor> interceptors, int index, Realcall realcall, Request request) {
        this.interceptors = interceptors;
        this.index = index;
        this.realcall = realcall;
        this.request = request;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        if (index >= interceptors.size()) throw new AssertionError();

        if (interceptors.isEmpty()) {
            throw new IOException("interceptors is empty");
        }

        Interceptor interceptor = interceptors.get(index);
        ChainManager chainManager = new ChainManager(interceptors,index + 1,realcall,request);
        Response response = interceptor.doNext(chainManager);

        return response;
    }
}
