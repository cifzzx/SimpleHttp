package com.cifz.simplehttp.simple;

/**
 * <pre>
 *     @author : wangzhen
 *     time   : 2020/03/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface Call extends Cloneable {
    void enqueue(Callback responseCallback);
}
