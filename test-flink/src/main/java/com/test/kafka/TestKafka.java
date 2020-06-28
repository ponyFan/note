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
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(TestFlink.getConsumerProperties());
            consumer.subscribe(Arrays.asList("test004"));
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("consume kafka msg : " + record.value());
            }
        }
    }
}
