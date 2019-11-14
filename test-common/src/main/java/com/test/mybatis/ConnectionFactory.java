package com.test.mybatis;

import com.test.jdbc.DriverEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zelei.fan
 * @date 2019/11/12 9:39
 */
public class ConnectionFactory {

    public static Connection getConnection(DriverEnum dialect, String url, String user, String pwd){
        try {
            Class.forName(dialect.getDriverClass());
            return DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            throw new RuntimeException("连接创建失败");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到指定的数据库驱动:" + dialect.getDriverClass());
        }
    }

}
