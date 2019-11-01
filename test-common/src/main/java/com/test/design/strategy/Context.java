package com.test.design.strategy;

/**
 * @author zelei.fan
 * @date 2019/11/1 11:41
 */
public class Context {

    private Strategy strategy;

    Context(Strategy strategy){
        this.strategy = strategy;
    }

    public double doConvert(double rmb){
        return strategy.convert(rmb);
    }
}
