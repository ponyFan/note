package com.test.broadcast;

import com.test.TestFlink;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zelei.fan
 * @date 2019/9/26 16:51
 */
public class CommonStreamTest {

    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.enableCheckpointing(5000);
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://172.22.6.3:3306/testDB?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "Tw#123456");
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT code,city FROM (SELECT DISTINCT code,city FROM city_code) t");
        while (resultSet.next()){
            map.put(resultSet.getString("code"), resultSet.getString("city"));
        }
        System.out.println("map size" + map.size());
        FlinkKafkaConsumer010<String> source = new FlinkKafkaConsumer010<>(args[0],
                new SimpleStringSchema(), TestFlink.getConsumerProperties());
        // 自定义数据流（单例）
        DataStream<String> dataStream = environment.addSource(source).setParallelism(29).map(new MyMapTest(map)).setParallelism(30);
        /*StreamingFileSink<String> build = StreamingFileSink.forBulkFormat(new Path("hdfs://hadoop01:9000/test/parquet"),
                ParquetAvroWriters.forReflectRecord(String.class)).build();
        dataStream.addSink(build).setParallelism(30);*/
        environment.execute();
    }
}
