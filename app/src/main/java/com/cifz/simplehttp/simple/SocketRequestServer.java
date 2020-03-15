package com.cifz.simplehttp.simple;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SocketRequestServer {

    private final String K = " ";
    private final String VIERSION = "HTTP/1.1";
    private final String GRGN = "\r\n";

    /**
     * 通过Request对象，寻找到域名HOST
     * @param request
     * @return
     */
    public String getHost(Request request) {
        try {
            URL url = new URL(request.getUrl());
            return url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPort(Request request){
        try {
            URL url = new URL(request.getUrl());
            int port = url.getPort();
            return port == -1 ? url.getDefaultPort() : port;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getRequestHeaderAll(Request request){
        URL url = null;
        try {
            url = new URL(request.getUrl());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        String file = url.getFile();
        // TODO 拼接 请求头 的 请求行  GET /v3/weather/weatherInfo?city=110101&key=13cb58f5884f9749287abbead9c658f2 HTTP/1.1\r\n
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(request.getRequestMethod())
                .append(K)
                .append(file)
                .append(K)
                .append(VIERSION)
                .append(GRGN);

        if(!request.getmHeaderList().isEmpty()){
            Map<String,String> mapLists = request.getmHeaderList();
            for (Map.Entry<String, String> stringStringEntry : mapLists.entrySet()) {
                stringBuffer.append(stringStringEntry.getKey())
                        .append(":")
                        .append(stringStringEntry.getValue())
                        .append(GRGN);
            }
            stringBuffer.append(GRGN);
        }

        if(TextUtils.equals("POST",request.getRequestMethod())){
            stringBuffer.append(request.getRequestBody().getBody()).append(GRGN);
        }
        return stringBuffer.toString();
    }

}
