package com.test.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zelei.fan
 * @date 2019/10/24 15:27
 */
public class AIOServer {

    public static void main(String[] args) {
        try {
            //1、创建缓存池
            ExecutorService pool = Executors.newCachedThreadPool();
            //2、创建通道组
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(pool, 1);
            //3、创建服务器通道
            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open(threadGroup);
            serverSocketChannel.bind(new InetSocketAddress(8083));
            /*serverSocketChannel.accept(this, );*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
