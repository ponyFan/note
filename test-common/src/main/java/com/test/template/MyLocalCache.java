package com.test.template;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * google guava localCache example
 * 不同线程共享该缓存
 */
public class MyLocalCache {

    static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1024)/*设置最大容量*/
            .expireAfterWrite(1, TimeUnit.MINUTES)/*设置失效时间*/
            .concurrencyLevel(2)/*设置并发级别*/
            .build();
    static ExecutorService threadPool = Executors.newFixedThreadPool(5);
    public static void main(String[] args) {
        cache.put("test", "wwwwwwww");
        while (true){
            for (int i = 0; i < 5; i++){
                threadPool.submit(() -> {
                    System.out.println( Thread.currentThread().getId()+ " get local cache " + cache.getIfPresent("test"));
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
