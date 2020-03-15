package com.cifz.simplehttp.simple;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RequestBody {
    // 表单提交Type application/x-www-form-urlencoded
    public static final String TYPE = "application/x-www-form-urlencoded";

    private final String ENC = "utf-8";

    // 请求体集合  a=123&b=666
    Map<String, String> bodys = new HashMap<>();

    public void addBody(String key, String value){
        try {
            bodys.put(URLEncoder.encode(key,ENC),URLEncoder.encode(value,ENC));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getBody(){
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> stringStringEntry : bodys.entrySet()) {
            // // a=123&b=666&
            stringBuffer.append(stringStringEntry.getKey())
                    .append("=")
                    .append(stringStringEntry.getValue())
                    .append("&");
        }
        if(stringBuffer.length() != 0){
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

}
