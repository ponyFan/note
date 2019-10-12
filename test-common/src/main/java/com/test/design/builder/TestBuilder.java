package com.test.design.builder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class TestBuilder {

    static AtomicInteger index = new AtomicInteger();
    static Semaphore semaphore = new Semaphore(10);
    public static class MyThread extends Thread{
        @Override
        public void run() {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程"+Thread.currentThread().getName()+"开始执行");
            semaphore.release();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*for (int i = 0; i < 100; i ++){
                *//*index.incrementAndGet();*//*
                index.compareAndSet(index.get(), i);
            }*/
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i ++){
            executorService.execute(new MyThread());
        }
    }
}
