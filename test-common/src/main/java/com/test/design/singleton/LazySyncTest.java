package com.test.design.singleton;

/**
 * @author zelei.fan
 * @date 2019/10/15 17:52
 */
public class LazySyncTest {

    private static LazySyncTest lazySyncTest;

    private LazySyncTest(){}

    public static synchronized LazySyncTest getInstance(){
        if (null == lazySyncTest){
            lazySyncTest = new LazySyncTest();
        }
        return lazySyncTest;
    }
}
