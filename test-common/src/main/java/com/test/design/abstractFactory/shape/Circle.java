package com.test.design.abstractFactory.shape;

/**
 * @author zelei.fan
 * @date 2019/10/30 15:33
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("this is circle");
    }
}
