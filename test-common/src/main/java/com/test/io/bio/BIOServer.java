package com.test.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO：同步阻塞IO
 * 模型: acceptor会为每个client端请求分配一个线程
 *  缺点：1、线程切换过多，浪费cpu资源
 *       2、每个线程长期占用一定内存作为线程栈，比如1000个线程同时运行，每个线程占用1M，则总的会占用1G
 *       3、主要还是阻塞原因，每个线程都是阻塞处理客户端请求
 * @author zelei.fan
 * @date 2019/10/21 19:18
 */
public class BIOServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 8081));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Socket finalSocket = socket;
            /*每来一个client端请求，就新建一个线程去处理*/
            new Thread(() -> {
                BufferedReader reader = null;
                PrintWriter writer = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                    writer = new PrintWriter(finalSocket.getOutputStream(), true);
                    String msg = null;
                    while (true){
                        msg = reader.readLine();
                        if (null == msg) break;
                        System.out.println("get msg < "+msg+" > from client");
                        writer.println(msg+"ssssssss");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (null != writer) {
                        writer.close();
                    }
                    try {
                        if (null != reader) {
                            reader.close();
                        }
                        if (null != finalSocket) {
                            finalSocket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
