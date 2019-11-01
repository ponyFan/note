package com.test.io.aio;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author zelei.fan
 * @date 2019/10/29 9:29
 */
public class AIOServerHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {
    @Override
    public void completed(AsynchronousSocketChannel result, AIOServer attachment) {

    }

    @Override
    public void failed(Throwable exc, AIOServer attachment) {

    }
}
