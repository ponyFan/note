package com.test.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 等待其他线程完成后再执行
 * @author zelei.fan
 * @date 2019/10/10 15:54
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        /*如果count大于0，那么await会一直阻塞，直到小于等于0*/
        MyCountDownLatch countDownLatch = new MyCountDownLatch(2);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i ++){
            pool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();//每执行一次减1，直到count计数为0，await开始执行
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("start to execute after threads");
        pool.shutdown();
    }
}
