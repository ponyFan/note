package com.test.kafka;

import com.test.TestFlink;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;

/**
 * 造数据
 * @author zelei.fan
 * @date 2019/9/24 16:41
 */
public class ProduceMsg {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> source = env.addSource(new RandomSource());
        source.addSink(new FlinkKafkaProducer010<String>(args[0], new SimpleStringSchema(),
                TestFlink.getProducerProperties())).setParallelism(60);
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
