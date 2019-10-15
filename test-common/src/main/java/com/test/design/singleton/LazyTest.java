package com.test.design.singleton;

/**
 * @author zelei.fan
 * @date 2019/10/15 17:36
 */
public class LazyTest {

    private volatile static LazyTest lazyTest;

    private LazyTest(){}

    public static LazyTest getInstance(){
        if (null == lazyTest){
            synchronized (LazyTest.class){
                if (null == lazyTest){
                    System.out.println("init lazyTest");
                    lazyTest =  new LazyTest();
                }
            }
        }
        return lazyTest;
    }
}
