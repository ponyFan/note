package com.test.design.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * 动态生成字节码，生成被代理类的子类，通过“继承”可以继承父类所有的公开方法，然后可以重写这些方法，在重写时对这些方法增强，这就是cglib的思想
 * @author zelei.fan
 * @date 2019/11/7 17:55
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        Car car = new Car();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(car.getClass());
        enhancer.setCallback(new MyEnhancer());
        Car proxy = (Car)enhancer.create();
        proxy.drive("ford");
    }
}
