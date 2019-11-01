package com.test.design.strategy;

/**
 * 日元汇率
 * @author zelei.fan
 * @date 2019/11/1 11:38
 */
public class Yen implements Strategy{

    @Override
    public double convert(double rmb) {
        return rmb*15.3438;
    }
}
