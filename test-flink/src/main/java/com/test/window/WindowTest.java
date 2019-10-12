package com.test.window;

import com.test.TestFlink;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.RichAllWindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

import javax.annotation.Nullable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * @author zelei.fan
 * @date 2019/9/30 14:18
 */
public class WindowTest {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        FlinkKafkaConsumer010<Row> source = new FlinkKafkaConsumer010<>("test006", new DeserializationSchema<Row>() {
            @Override
            public Row deserialize(byte[] bytes) throws IOException {
                String s = new String(bytes);
                try {
                    if (StringUtils.isNotBlank(s)) {
                        String[] split = StringUtils.split(s, ",");
                        Row row = new Row(2);
                        row.setField(0, split[0]);
                        row.setField(1, split[1]);
                        return row;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public boolean isEndOfStream(Row row) {
                return false;
            }

            @Override
            public TypeInformation<Row> getProducedType() {
                return TypeInformation.of(Row.class);
            }
        }, TestFlink.getConsumerProperties(""));
        env.addSource(source)
                .assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Row>() {
                    long  currentMaxTimestamp = 0L;
                    long  maxOutOfOrderness = 5000L;
                    Watermark watermark=null;

                    @Nullable
                    @Override
                    public Watermark getCurrentWatermark() {
                        return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
                    }

                    @Override
                    public long extractTimestamp(Row row, long l) {
                        long timeStamp = Long.valueOf((String) row.getField(0));
                        currentMaxTimestamp = Math.max(timeStamp, currentMaxTimestamp);
                        System.out.println("currentMaxTimestamp is : " + currentMaxTimestamp);
                        System.out.println("timestamp is : " + timeStamp);
                        return timeStamp ;
                    }
                })
                .timeWindowAll(Time.seconds(Long.valueOf(args[0]))).apply(new RichAllWindowFunction<Row, Row, TimeWindow>() {
            @Override
            public void apply(TimeWindow timeWindow, Iterable<Row> iterable, Collector<Row> collector) throws Exception {
                Iterator<Row> iterator = iterable.iterator();
                String msg = "";
                while (iterator.hasNext()){
                    Row next = iterator.next();
                    collector.collect(next);
                }
            }
        }).map(new MapFunction<Row, Row>() {
            @Override
            public Row map(Row row) throws Exception {
                System.out.println("current time is : " + sf.format(new Date()));
                return row;
            }
        }).print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
