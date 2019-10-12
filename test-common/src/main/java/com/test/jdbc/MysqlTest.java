package com.test.jdbc;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

/**
 * @author zelei.fan
 * @date 2019/9/26 14:07
 */
public class MysqlTest {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://172.22.6.3:3306/testDB?useUnicode=true&characterEncoding=utf-8", "root", "Tw#123456");
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("insert into city_code (city, code) values(?,?)");
            Faker faker = new Faker(Locale.CHINA);
            int index = 0;
            for (int i = 0; i < 100000; i++) {
                Address address = faker.address();
                ps.setString(1, address.streetAddress());
                ps.setString(2, faker.address().buildingNumber());
                if (i % 1000 == 0){
                    ps.executeBatch();
                    connection.commit();
                    ps.clearBatch();
                    index ++;
                    System.out.println("this is " + index +" batch!");
                }
                ps.addBatch();//添加到批次
            }
            ps.executeBatch();//提交批处理
            connection.commit();//执行
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
