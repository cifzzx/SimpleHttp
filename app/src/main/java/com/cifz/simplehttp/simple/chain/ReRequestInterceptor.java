package com.cifz.simplehttp.simple.chain;

import android.util.Log;

import com.cifz.simplehttp.simple.OkHttpClient;
import com.cifz.simplehttp.simple.Realcall;
import com.cifz.simplehttp.simple.Request;
import com.cifz.simplehttp.simple.Response;

import java.io.IOException;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ReRequestInterceptor implements Interceptor {

    private static final String TAG = "MainActivityTag";
    
    @Override
    public Response doNext(Chain chain) throws IOException {

        ChainManager chainManager = (ChainManager) chain;
        Realcall realcall = chainManager.getRealcall();
        OkHttpClient okHttpClient = realcall.getOkHttpClient();
        IOException ioException = null;
        for (int i = 0; i < okHttpClient.getReCount(); i++) {
            try {
                Log.e(TAG,"我是重试拦截器，我要Return Response2");
                Response response = chain.getResponse(chainManager.getRequest());
                return response;
            }catch (IOException e){
               ioException = e;
            }
        }
        throw ioException;
    }
}
