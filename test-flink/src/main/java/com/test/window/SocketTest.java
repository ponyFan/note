package com.test.window;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamContextEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zelei.fan
 * @date 2019/10/8 9:42
 */
public class SocketTest {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamContextEnvironment.getExecutionEnvironment();
        DataStreamSource<String> socketStream = env.socketTextStream("localhost", 8000);
        socketStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                System.out.println("get msg : " + s);
                return s;
            }
        }).print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
