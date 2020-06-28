package com.test.broadcast;

import com.test.TestFlink;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * @author zelei.fan
 * @date 2019/9/26 14:01
 */
public class BroadcastTest {

    public static void main(String[] args) throws Exception {
        // 构建流处理环境
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 配置处理环境的并发度为4
        environment.enableCheckpointing(5000);
        final MapStateDescriptor<String, String> CONFIG_KEYWORDS = new MapStateDescriptor<>(
                "config-keywords",
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO);
        // 自定义广播流（单例）
        BroadcastStream<Map> broadcastStream = environment.addSource(new RichSourceFunction<Map>() {
            private volatile boolean isRunning = true;
            private Connection connection;
            private Map<String, String> map = new HashMap<>();

            @Override
            public void open(Configuration parameters) throws Exception {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://172.22.6.3:3306/testDB?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "Tw#123456");
            }

            /**
             * 数据源：模拟每10秒随机更新一次拦截的关键字
             * @param ctx
             * @throws Exception
             */
            @Override
            public void run(SourceContext<Map> ctx) throws Exception {
                while (isRunning) {
                    TimeUnit.SECONDS.sleep(10);
                    if (map.size() == 0){
                        /*long l = System.currentTimeMillis();*/
                        ResultSet resultSet = connection.createStatement().executeQuery("SELECT code,city FROM (SELECT DISTINCT code,city FROM city_code) t");
                        while (resultSet.next()){
                            map.put(resultSet.getString("code"), resultSet.getString("city"));
                        }
                        /*long l1 = System.currentTimeMillis();
                        System.out.println("query mysql time : " + (l1 - l));*/
                    }
                    //随机选择关键字发送
                    ctx.collect(map);
                }
            }

            @Override
            public void cancel() {
                isRunning = false;
            }
        }).setParallelism(1).broadcast(CONFIG_KEYWORDS);

        FlinkKafkaConsumer010<String> source = new FlinkKafkaConsumer010<>(args[0],
                new SimpleStringSchema(), TestFlink.getConsumerProperties());
        // 自定义数据流（单例）
        DataStream<String> dataStream = environment.addSource(source).setParallelism(30);

        // 数据流和广播流连接处理并将拦截结果打印
        dataStream.connect(broadcastStream).process(new BroadcastProcessFunction<String, Map, String>() {

            //拦截的关键字
            private Map<String, String> map = new HashMap<>();

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
            }

            @Override
            public void processElement(String value, ReadOnlyContext ctx, Collector<String> out) throws Exception {
                if (StringUtils.isNotBlank(value)){
                    String[] split = StringUtils.split(value, ",");
                    String s = map.get(split[0]);
                    if (null == s){
                        split[0] = "default";
                    }else {
                        split[0] = map.get(split[0]);
                    }
                    /*System.out.println("get msg : " + StringUtils.join(split, ","));*/
                    out.collect(StringUtils.join(split, ","));
                }
            }

            @Override
            public void processBroadcastElement(Map value, Context ctx, Collector<String> out) throws Exception {
                map = value;
            }
        }).setParallelism(30);
        // 懒加载执行
        environment.execute();
    }

}
