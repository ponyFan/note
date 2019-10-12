package com.test.concurrent;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  回环栅栏，达到屏障设置的同步点后才开始执行
 * @author zelei.fan
 * @date 2019/10/10 16:21
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        System.out.println(new Date());
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 15; i ++){
            pool.execute(() -> {
                try {
                    cyclicBarrier.await();/*等待到wait数达到初始时设置的屏障点时才开始执行*/
                    System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
