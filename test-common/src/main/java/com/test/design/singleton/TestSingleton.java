package com.test.design.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zelei.fan
 * @date 2019/10/15 17:42
 */
public class TestSingleton {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i ++){
            pool.execute(() -> {
//                System.out.println("lazyTest : " + LazyTest.getInstance());
//                System.out.println("lazySyncTest : " + LazySyncTest.getInstance());
                System.out.println("unLazyTest : " + UnLazyTest.getInstance());
            });
        }
        pool.shutdown();
    }
}
