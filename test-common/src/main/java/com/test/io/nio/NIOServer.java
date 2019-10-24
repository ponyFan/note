package com.test.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO：非阻塞IO，因为它是通过轮询的方式去监听事件，不是一直阻塞，但也不是异步
 * Buffer   ：缓冲区，NIO数据操作都是在缓冲区进行。缓冲区实际是一个数组；而BIO是将数据直接写入或读取到stream对象
 * Channel  ：通道，NIO可以通过channel进行数据的读/写操作
 * Selector ：多路复用器，提供选择已经就绪状态任务的能力
 * 客户端和服务器通过channel连接，而这些channel都要注册在selector。selector通过一个线程不停的轮询这些channel
 * @author zelei.fan
 * @date 2019/10/22 9:17
 */
public class NIOServer {

    static final int BUFFER_SIZE = 1024;
    static final int PORT = 8082;
    static ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
    static Selector selector;

    public static void main(String[] args) {
        //1、启动服务
        startServer();
        //2、启动单线程轮询selector来处理客户端请求事件
        new Thread(() -> {
            while (true){
                try {
                    //1、多路复用器监听
                    /**
                     * a.select() 阻塞到至少有一个通道在你注册的事件上就绪
                     * b.select(long timeOut) 阻塞到至少有一个通道在你注册的事件上就绪或者超时timeOut
                     * c.selectNow() 立即返回。如果没有就绪的通道则返回0
                     * select方法的返回值表示就绪通道的个数。
                     */
                    selector.select();
                    //2、获取多路复用器已经选择的结果集
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    //3、轮询
                    while (keyIterator.hasNext()){
                        //4、获取到一个选中的key
                        SelectionKey key = keyIterator.next();
                        //5、获取后便将其从容器中移除
                        keyIterator.remove();
                        //6、判断key是否有效
                        if (!key.isValid()){
                            continue;
                        }
                        //7、阻塞状态处理
                        if (key.isAcceptable()){
                            //设置阻塞，等待client请求；
                            //1、获取通道服务
                            ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                            //2、执行阻塞方法
                            SocketChannel socketChannel = channel.accept();
                            //3、设置服务器通道为非阻塞
                            socketChannel.configureBlocking(false);
                            //4、把通道注册到多路复用器上，并设置读取标志
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                        //读取状态处理
                        if (key.isReadable()){
                            //1、清空缓冲区数据
                            buffer.clear();
                            //2、获取再多路复用器上注册的通道
                            SocketChannel channel = (SocketChannel)key.channel();
                            //3、读取数据
                            int read = channel.read(buffer);
                            //4、返回-1表示没有数据，关闭通道
                            if (-1 == read){
                                key.channel().close();
                                key.cancel();
                                return;
                            }
                            //5、如果有数据，则在读取数据前进行复位操作
                            buffer.flip();
                            //6、根据缓冲区大小创建一个相应大小的bytes数组，用来获取值
                            byte[] bytes = new byte[buffer.remaining()];
                            //7、接收缓冲区数据
                            buffer.get(bytes);
                            //8、打印获取的字节数组
                            System.out.println("server get msg : " + new String(bytes));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void startServer(){
        try {
            //1、开启多路复用器
            selector = Selector.open();
            //2、打开服务器通道（网络读写通道）
            ServerSocketChannel channel = ServerSocketChannel.open();
            //3、设置服务通道为非阻塞，true为阻塞，false为非阻塞
            channel.configureBlocking(false);
            //4、绑定端口
            channel.socket().bind(new InetSocketAddress("localhost", PORT));
            //5、把通道注册到多路复用器上，并监听阻塞事件
            /**
             * SelectionKey.OP_READ     : 表示关注读数据就绪事件
             * SelectionKey.OP_WRITE     : 表示关注写数据就绪事件
             * SelectionKey.OP_CONNECT: 表示关注socket channel的连接完成事件
             * SelectionKey.OP_ACCEPT : 表示关注server-socket channel的accept事件
             */
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("start server and port is : " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
