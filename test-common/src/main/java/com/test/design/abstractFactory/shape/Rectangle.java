package com.test.design.abstractFactory.shape;

/**
 * 矩形
 * @author zelei.fan
 * @date 2019/10/30 15:25
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("this is Rectangle");
    }
}
