package com.test.design.strategy;

/**
 * 策略模式
 *
 * 根据不同的实例进行不同的换算
 *
 * @author zelei.fan
 * @date 2019/11/1 11:40
 */
public class Test {

    public static void main(String[] args) {
        Context context = new Context(new Dollar());
        double dollar = context.doConvert(100);
        System.out.println("100 rmb convert to dollar is : " + dollar);
        Context context1 = new Context(new Won());
        double v = context1.doConvert(100);
        System.out.println("100 rmb convert to korea won is : " + v);
    }
}
