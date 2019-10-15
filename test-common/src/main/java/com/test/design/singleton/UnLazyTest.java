package com.test.design.singleton;

/**
 * @author zelei.fan
 * @date 2019/10/15 17:50
 */
public class UnLazyTest {

    private static UnLazyTest unLazyTest = new UnLazyTest();

    private UnLazyTest(){}

    public static UnLazyTest getInstance(){
        return unLazyTest;
    }
}
