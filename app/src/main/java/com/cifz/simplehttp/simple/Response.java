package com.cifz.simplehttp.simple;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Response {
    private String body;
    private int stateCode;
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String string(){
        return body;
    }
}
