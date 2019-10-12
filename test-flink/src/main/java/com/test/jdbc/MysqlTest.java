package com.test.jdbc;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;

/**
 * @author zelei.fan
 * @date 2019/9/26 14:38
 */
public class MysqlTest {
    public static void main(String[] args) {
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        JDBCInputFormat jdbcInputFormat = JDBCInputFormat.buildJDBCInputFormat()
                .setDrivername("com.mysql.jdbc.Driver")
                .setDBUrl("jdbc:mysql://172.22.6.3:3306/testDB?useUnicode=true&characterEncoding=utf-8")
                .setUsername("root")
                .setPassword("Tw#123456")
                .setQuery("select * from student")
                .setRowTypeInfo(new RowTypeInfo(
                        BasicTypeInfo.INT_TYPE_INFO,
                        BasicTypeInfo.INT_TYPE_INFO,
                        BasicTypeInfo.STRING_TYPE_INFO,
                        BasicTypeInfo.INT_TYPE_INFO,
                        BasicTypeInfo.STRING_TYPE_INFO))
                .finish();
        DataSource<Row> input = environment.createInput(jdbcInputFormat);
        MapOperator<Row, Row> map = input.map(new MapFunction<Row, Row>() {
            @Override
            public Row map(Row row) throws Exception {
                System.out.println("map row " + row);
                return row;
            }
        });
        try {
            map.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
