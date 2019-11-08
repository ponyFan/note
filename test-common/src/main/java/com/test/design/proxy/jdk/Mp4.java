package com.test.design.proxy.jdk;

/**
 * @author zelei.fan
 * @date 2019/11/7 15:11
 */
public class Mp4 implements Radio {
    @Override
    public void play(String type) {
        System.out.println("play " + type);
    }
}
