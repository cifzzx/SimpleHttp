package com.cifz.simplehttp.simple;

import android.os.Bundle;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class OkHttpClient {
    Dispatcher dispatcher;
    boolean isCancle;
    private int reCount;

    public boolean getCanceled() {
        return isCancle;
    }

    public int getReCount() {
        return reCount;
    }

    public OkHttpClient(Builder builder) {
        dispatcher = builder.dispatcher;
        isCancle = builder.isCancle;
        reCount = builder.reCount;
    }

    public OkHttpClient() {
        this(new Builder());
    }

    public final static class Builder{
        Dispatcher dispatcher;
        boolean isCancle;
        int reCount;

        public Builder reCount(int reCount){
            this.reCount = reCount;
            return this;
        }

        public Builder dispatcher(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder cancel(){
            this.isCancle = true;
            return this;
        }

        public OkHttpClient build(){
            return new OkHttpClient(this);
        }
    }

    public Call newCall(Request request){
        return new Realcall(this,request);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }
}
