package com.test.mybatis;

import com.google.common.collect.Lists;
import com.test.jdbc.DriverEnum;
import lombok.Data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zelei.fan
 * @date 2019/11/12 11:03
 */
public class Test {

    public static void main(String[] args) throws Exception{
        Connection connection = ConnectionFactory.getConnection(DriverEnum.MYSQL, "jdbc:mysql://172.22.6.3:3306", "root", "Tw#123456");
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet meta = metaData.getColumns(null, null, null, null);
        while (meta.next()){
            System.out.println(meta.getString("TABLE_CAT")
                    +"."+meta.getString("TABLE_NAME")
                    +"-->"+meta.getString("COLUMN_NAME")
                    +"--"+meta.getString("TYPE_NAME")
                    +"--"+meta.getString("REMARKS"));
        }
    }

    /**
     * 获取数据库
     * @param metaData
     * @return
     */
    public static List<String> getDBs(DatabaseMetaData metaData){
        List<String> dbList = Lists.newArrayList();
        try {
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()){
                dbList.add(catalogs.getString("TABLE_CAT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbList;
    }

    public static List queryConnection(String id){
        return null;
    }

    public static List queryAllConnection(){
        return null;
    }

    public static List queryDB(String connectionId){
        return null;
    }

    public static List queryTables(String connectionId, String db){
        return null;
    }

    @Data
    static class TableInfo{
        private String id;
        private String connectionId;
        private String db;
        private String dbCode;
        private String table;
        private String tableCode;
        private String column;
        private String type;
        private String remark;
    }
}
