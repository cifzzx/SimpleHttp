package com.cifz.simplehttp.simple.chain;

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
public interface Chain {
    Request getRequest();

    Response getResponse(Request request) throws IOException;
}
