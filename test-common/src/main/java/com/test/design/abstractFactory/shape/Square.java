package com.test.design.abstractFactory.shape;

/**
 * 正方形
 * @author zelei.fan
 * @date 2019/10/30 15:30
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("this is square");
    }
}
