package com.test.design.staticFactory;

/**
 * 简单/静态工厂模式
 *                |----apple<fruit>
 * fruitFactory---|
 *                |----banana<fruit>
 * @author zelei.fan
 * @date 2019/10/30 14:10
 */
public class Test {

    public static void main(String[] args) {
        Fruit apple = FruitFactory.getFruit("apple");
        apple.name();
    }
}
