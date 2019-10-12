package com.test.kafka;

import com.test.TestFlink;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;

/**
 * @author zelei.fan
 * @date 2019/9/24 17:51
 */
public class TestKafka {

    public static void main(String[] args) {
        while (true){
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(TestFlink.getConsumerProperties("172.22.6.19:9092,172.22.6.20:9092,172.22.6.21:9092"));
            consumer.subscribe(Arrays.asList("test004"));
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("consume kafka msg : " + record.value());
            }
        }
    }
}
