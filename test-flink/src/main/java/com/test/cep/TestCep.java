package com.test.cep;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamContextEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.Tuple3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * cep测试类
 * @author zelei.fan
 * @date 2019/9/25 13:51
 */
public class TestCep {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamContextEnvironment.getExecutionEnvironment();
        ArrayList<Tuple3> list = new ArrayList<>();
        list.add(new Tuple3<>("1", "192", "success"));
        list.add(new Tuple3<>("1", "192.168.0.1", "fail"));
        list.add(new Tuple3<>("1", "192.168.0.2", "fail"));
        list.add(new Tuple3<>("1", "192.168.0.3", "fail"));
        list.add(new Tuple3<>("2", "192.168.10,10", "success"));
        DataStreamSource<Tuple3> source = env.fromCollection(list);
        Pattern<Tuple3, Tuple3> pattern = Pattern.<Tuple3>begin("start").where(new IterativeCondition<Tuple3>() {
            @Override
            public boolean filter(Tuple3 tuple3, Context<Tuple3> context) throws Exception {
                boolean equals = tuple3._1().equals("2");
                System.out.println("filter is : " + equals + "and msg : " + tuple3);
                return equals;
            }
        });
        PatternStream<Tuple3> pattern1 = CEP.pattern(source, pattern);
        SingleOutputStreamOperator<List<Tuple3>> select = pattern1.select(new PatternSelectFunction<Tuple3, List<Tuple3>>() {
            @Override
            public List<Tuple3> select(Map<String, List<Tuple3>> map) throws Exception {
                List<Tuple3> start = map.get("start");
                System.out.println("start is : " + start.toString());
                return start;
            }
        });
        select.print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
