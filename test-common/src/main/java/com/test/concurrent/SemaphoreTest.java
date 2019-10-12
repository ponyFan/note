package com.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量，控制并发访问最大访问数，可以用于控制连接池获取的连接数
 * @author zelei.fan
 * @date 2019/10/10 13:45
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        int a = 0;
        if (a > 1 || ((a = 2) > 0)){
            System.out.println(a);
        }
        System.out.println(a);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(10, true);//设置并发访问数和是否公平，true为公平即先阻塞的先执行
        for (int i = 0;i < 100; i ++){
            pool.execute(() -> {
                try {
                    /*每次获取一个许可，当达到设置的访问数时，后续线程阻塞直到前面一批释放*/
                    semaphore.acquire();
                    System.out.println("current thread ：" + Thread.currentThread().getName());
                    Thread.sleep(2000);
                    semaphore.release();
                    System.out.println("release thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();
    }

}
