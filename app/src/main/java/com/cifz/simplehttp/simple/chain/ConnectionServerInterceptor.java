package com.cifz.simplehttp.simple.chain;

import android.util.Log;

import com.cifz.simplehttp.R;
import com.cifz.simplehttp.simple.Request;
import com.cifz.simplehttp.simple.Response;
import com.cifz.simplehttp.simple.SocketRequestServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/15
 *     desc   : 网络连接拦截器
 *     version: 1.0
 * </pre>
 */
public class ConnectionServerInterceptor implements Interceptor {
    private static final String TAG = "MainActivityTag";
    @Override
    public Response doNext(Chain chain) throws IOException {
        SocketRequestServer srs = new SocketRequestServer();
        ChainManager chainManager = (ChainManager) chain;
        Request request = chainManager.getRequest();
        Socket socket = new Socket(srs.getHost(request), srs.getPort(request));
        OutputStream os = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));

        String requestAll = srs.getRequestHeaderAll(request);

        bufferedWriter.write(requestAll);
        bufferedWriter.flush();

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Response response = new Response();
        String oneLine = bufferedReader.readLine();
        String[] strings = oneLine.split(" ");
        response.setStateCode(Integer.valueOf(strings[1]));

        String content = null;

        try {

            while ((content = bufferedReader.readLine()) != null){
                if("".equals(content)){
                    response.setBody(bufferedReader.readLine());
                    break;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
