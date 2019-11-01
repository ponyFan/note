package com.test.design.staticFactory;

/**
 * @author zelei.fan
 * @date 2019/10/30 14:10
 */
public class FruitFactory {

    public static Fruit getFruit(String name){
        switch (name){
            case "apple":
                return new Apple();
            case "banana":
                return new Banana();
            default:
                return null;
        }
    }
}
