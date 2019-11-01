package com.test.design.strategy;

/**
 * 美元汇率
 * @author zelei.fan
 * @date 2019/11/1 11:35
 */
public class Dollar implements Strategy{

    @Override
    public double convert(double rmb) {
        return rmb*0.142;
    }
}
