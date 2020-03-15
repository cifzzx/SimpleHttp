package com.cifz.simplehttp.simple.chain;

import android.text.TextUtils;

import com.cifz.simplehttp.simple.OkHttpClient;
import com.cifz.simplehttp.simple.Realcall;
import com.cifz.simplehttp.simple.Request;
import com.cifz.simplehttp.simple.RequestBody;
import com.cifz.simplehttp.simple.Response;
import com.cifz.simplehttp.simple.SocketRequestServer;

import java.io.IOException;
import java.util.Map;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RequestHeaderInterceptor implements Interceptor {
    @Override
    public Response doNext(Chain chain) throws IOException {

        ChainManager chainManager = (ChainManager) chain;
        Realcall realcall = chainManager.getRealcall();
        OkHttpClient okHttpClient = realcall.getOkHttpClient();
        Request request = chainManager.getRequest();


        Map<String,String> httpHeaderList = request.getmHeaderList();
        httpHeaderList.put("Host",new SocketRequestServer().getHost(request));
        if(TextUtils.equals("POST",request.getRequestMethod())){
            /**
             * Content-Length: 48
             * Content-Type: application/x-www-form-urlencoded
             */
            httpHeaderList.put("Content-Length", request.getRequestBody().getBody().length()+"");
            httpHeaderList.put("Content-Type", RequestBody.TYPE);
        }

        return chain.getResponse(request);
    }
}
