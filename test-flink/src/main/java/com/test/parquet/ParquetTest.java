package com.test.parquet;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.parquet.avro.ParquetAvroWriters;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author zelei.fan
 * @date 2019/9/27 0:01
 */
public class ParquetTest {

    public static void main(String[] args) {
        final List<Datum> data = Arrays.asList(
                new Datum("a", 1), new Datum("b", 2), new Datum("c", 3));
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStream<Datum> stream = env.addSource(
                new FiniteTestSource<>(data), TypeInformation.of(Datum.class));
        stream.addSink(
                StreamingFileSink.forBulkFormat(
                        Path.fromLocalFile(new File("E:\\parquet")),
                        ParquetAvroWriters.forReflectRecord(Datum.class))
                        .build());
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Datum implements Serializable {

        public String a;
        public int b;

        public Datum() {}

        public Datum(String a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Datum datum = (Datum) o;
            return b == datum.b && (a != null ? a.equals(datum.a) : datum.a == null);
        }

        @Override
        public int hashCode() {
            int result = a != null ? a.hashCode() : 0;
            result = 31 * result + b;
            return result;
        }
    }
}
