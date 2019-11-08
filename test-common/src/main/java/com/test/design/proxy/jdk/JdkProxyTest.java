package com.test.design.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * jdk动态代理：先生成class文件，然后加载到jvm中，使用反射获取构造方法，然后new一个实例
 * 因为jdk是基于反射,CGLIB是基于字节码.所以性能上会有差异
 * @author zelei.fan
 * @date 2019/11/7 15:23
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        Mp4 mp4 = new Mp4();
        ClassLoader classLoader = mp4.getClass().getClassLoader();
        Class<?>[] interfaces = mp4.getClass().getInterfaces();
        RadioHandler radioHandler = new RadioHandler(mp4);
        Object o = Proxy.newProxyInstance(classLoader, interfaces, radioHandler);
        Radio radio = (Radio) o;
        radio.play("mp4");
    }
}
