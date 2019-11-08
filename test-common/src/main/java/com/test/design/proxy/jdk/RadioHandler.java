package com.test.design.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zelei.fan
 * @date 2019/11/7 15:12
 */
public class RadioHandler implements InvocationHandler {

    private Mp4 mp4;

    RadioHandler(Mp4 mp4){
        this.mp4 = mp4;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before play radio....");
        method.invoke(mp4, args[0]);
        System.out.println("after play radio....");
        return null;
    }
}
