package com.test;

import com.test.kafka.MessageBean;
import com.test.kafka.MySchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;

import java.util.Properties;

/**
 * @author zelei.fan
 * @date 2019/9/2 17:00
 */
public class TestFlink {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        FlinkKafkaConsumer010<MessageBean> source = new FlinkKafkaConsumer010<>(args[0],
                new MySchema(), getConsumerProperties(args[2]));
        DataStream<MessageBean> stream = env.addSource(source);
        DataStream<String> stream1 = stream.map(e -> e.toString());
        stream1.addSink(new FlinkKafkaProducer010<String>(args[1], new SimpleStringSchema(), getProducerProperties(args[2])));
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties getConsumerProperties(String server){
        Properties props=new Properties();
        props.put("bootstrap.servers", "172.22.6.19:9092,172.22.6.20:9092,172.22.6.21:9092");
        props.put("group.id", "cccccvvvvffffdddeeewwwwrrrreee");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    public static Properties getProducerProperties(String server){
        Properties props=new Properties();
        props.put("bootstrap.servers", "172.22.6.19:9092,172.22.6.20:9092,172.22.6.21:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        return props;
    }
}
