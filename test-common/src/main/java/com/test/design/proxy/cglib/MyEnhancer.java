package com.test.design.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zelei.fan
 * @date 2019/11/7 17:36
 */
public class MyEnhancer implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("start intercept......");
        methodProxy.invokeSuper(o, objects);
        System.out.println("after intercept.......");
        return null;
    }
}
