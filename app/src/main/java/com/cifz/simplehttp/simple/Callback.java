package com.cifz.simplehttp.simple;

import java.io.IOException;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface Callback {
    void onFailure(Call call, IOException e);

    void onResponse(Call call, Response response) throws IOException;
}
