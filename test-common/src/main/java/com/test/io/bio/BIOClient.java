package com.test.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zelei.fan
 * @date 2019/10/21 19:48
 */
public class BIOClient {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 1000; i++){
            pool.execute(() -> {
                Socket socket = null;
                BufferedReader reader = null;
                PrintWriter writer = null;
                try {
                    socket = new Socket("localhost", 8081);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(Thread.currentThread().getName()+ " client.....");
                    System.out.println("get msg from server : " + reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (null != reader) {
                            reader.close();
                        }
                        if (null != socket) {
                            socket.close();
                            socket = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }
}
