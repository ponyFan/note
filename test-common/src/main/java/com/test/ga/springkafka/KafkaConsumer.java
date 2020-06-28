package com.test.ga.springkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消费者，注入bean之后，会一直监听对应topic的消息，然后进行方法中的处理
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"${kafka.topic}"})
    public void consume(ConsumerRecord<String, String> record){
        String value = record.value();
        System.out.println("consumer msg is : " + value);
    }
}
