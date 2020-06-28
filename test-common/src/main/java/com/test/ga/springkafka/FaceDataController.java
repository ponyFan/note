package com.test.ga.springkafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaceDataController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    /**
     * 发送kafka消息
     * @param dataModel
     */
    @RequestMapping(value = "/face", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void consoleData(@RequestBody FaceDataModel dataModel){
        System.out.println(dataModel.toString());
        kafkaTemplate.send(topic, dataModel.toString());
    }
}
