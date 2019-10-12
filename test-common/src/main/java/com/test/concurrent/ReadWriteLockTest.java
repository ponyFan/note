package com.test.concurrent;

import com.test.util.DateTimeUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁，解决读之间互斥的问题，比如某个代码块加锁后，同一时刻只能有一个线程进入；
 *        但是如果只是多个线程并发去读取同步代码块中值而已，线程间互不影响，没必要用锁，
 *        此时就衍生出读写锁，写锁之间互斥，写锁与读锁互斥，读锁之间时共享
 * @author zelei.fan
 * @date 2019/10/10 14:39
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        /*模拟写操作
        * 同时运行10个线程，
        *   1、如果全是读锁，10个线程同时执行；说明读锁之间是共享
        *   2、如果一半读，一半写，则写锁之间每个都要等待，而读锁则需要等待写锁释放才能获取读锁；
        * 不管线程当前获取的是读锁还是写锁，其他线程都不能获得另一个锁，除非等待释放
        * */
        for (int i = 0; i < 5; i ++){
            pool.execute(() -> {
                try {
                    lock.writeLock().lock();
                    System.out.println(DateTimeUtil.getCurrentDateString() +"write data ....");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.writeLock().unlock();
                }
            });
        }
        /*模拟读操作*/
        for (int i = 0; i < 5; i ++){
            pool.execute(() -> {
                try {
                    lock.readLock().lock();
                    System.out.println(DateTimeUtil.getCurrentDateString()+"read data ....");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.readLock().unlock();
                }
            });
        }
        pool.shutdown();
    }
}
