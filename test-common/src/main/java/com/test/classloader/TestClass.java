package com.test.classloader;

/**
 * @author zelei.fan
 * @date 2019/11/7 10:14
 */
public class TestClass {

    public String value;

    public TestClass(){
    }

    public void getValue(){
        System.out.println("get value is " + value);
    }

    public void setValue(String value){
        System.out.println("set value is " + value);
    }
}
