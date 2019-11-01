package com.test.design.strategy;

/**
 * 韩元汇率
 * @author zelei.fan
 * @date 2019/11/1 11:39
 */
public class Won implements Strategy{

    @Override
    public double convert(double rmb) {
        return rmb*165.7648;
    }
}
