package com.cifz.simplehttp.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Request {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private String url;
    private String requestMethod = GET;
    private Map<String,String> mHeaderList = new HashMap<>();
    private RequestBody requestBody;

    public Request(Builder builder) {
        this.url = builder.url;
        this.requestMethod = builder.requestMethod;
        this.mHeaderList = builder.mHeaderList;
        this.requestBody = builder.requestBody;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> getmHeaderList() {
        return mHeaderList;
    }

    public final static class Builder{
        private String url;
        private String requestMethod = GET;
        private Map<String,String> mHeaderList = new HashMap<>();
        private RequestBody requestBody;
        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder get(){
            requestMethod = GET;
            return this;
        }

        public Builder post(RequestBody requestBody){
            requestMethod = POST;
            this.requestBody = requestBody;
            return this;
        }

        public Builder addRequestHeader(String key,String value){
            mHeaderList.put(key,value);
            return this;
        }

        public Request build(){
            return new Request(this);
        }
    }

}
