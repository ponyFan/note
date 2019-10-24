package com.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zelei.fan
 * @date 2019/10/22 10:16
 */
public class NIOClient {

    static final int BUFFER_SIZE = 1024;
    static final int PORT = 8082;
    static ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

    public static void main(String[] args) {
        SocketChannel channel = null;
        try {
            //1、打开通道
            channel = SocketChannel.open();
            //2、连接服务器
            channel.connect(new InetSocketAddress("localhost", PORT));
            while (true){
                //3、定义一个字节数组
                byte[] bytes = new byte[BUFFER_SIZE];
                //4、通过键盘输入数据
                System.in.read(bytes);
                //5、把数据放到缓冲区中
                buffer.put(bytes);
                //6、对缓冲区进行复位
                buffer.flip();
                //7、写出数据
                channel.write(buffer);
                //8、清空缓冲区数据
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != channel) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
