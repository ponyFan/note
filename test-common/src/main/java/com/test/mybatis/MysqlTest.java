package com.test.mybatis;

import com.github.abel533.database.DatabaseConfig;
import com.github.abel533.database.IntrospectedColumn;
import com.github.abel533.database.IntrospectedTable;
import com.github.abel533.database.SimpleDataSource;
import com.github.abel533.utils.DBMetadataUtils;
import com.test.jdbc.DriverEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author zelei.fan
 * @date 2019/11/11 11:32
 */
@RestController
@RequestMapping("/mysql")
public class MysqlTest {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String pwd;

    @Autowired
    private QuerySql sql;

    @RequestMapping("/test")
    public List getDBInfo(){
        return sql.select();
    }

    @RequestMapping("/insert")
    @Transactional
    public void insertMetaData(String cId){
        ConnectionDO connectionDO = sql.selectConnectionInfoById(cId);
        String url = connectionDO.getUrl();
        String user = connectionDO.getUser();
        String pwd = connectionDO.getPwd();
        Connection connection = null;
        DriverEnum driver = null;
        switch (connectionDO.getType()){
            case "mysql":
                driver = DriverEnum.MYSQL;
                break;
            case "oracle":
                driver = DriverEnum.ORACLE;
                break;
            case "hive":
                driver = DriverEnum.HIVE;
                break;
            default:
                driver = DriverEnum.MYSQL;
                break;
        }
        connection = ConnectionFactory.getConnection(driver, url, user, pwd);
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet meta = metaData.getColumns(null, null, null, null);
            List<TableInfoDO> list = new ArrayList<>();
            while (meta.next()){
                TableInfoDO infoDO = TableInfoDO.builder()
                        .id(UUID.randomUUID().toString())
                        .connectionId(cId)
                        .db(meta.getString("TABLE_CAT"))
                        .tableName(meta.getString("TABLE_NAME"))
                        .columnName(meta.getString("COLUMN_NAME"))
                        .columnType(meta.getString("TYPE_NAME"))
                        .remark(meta.getString("REMARKS")).build();
                list.add(infoDO);
            }
            sql.delete(cId, null, null);
            sql.insertTables(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/db")
    public List<String> selectDb(String cId){
        List<String> list = sql.selectByCid(cId);
        return list;
    }

    @RequestMapping("/tables")
    public List<String> selectTables(String cId, String db){
        List<TableInfoDO> list = sql.selectTableInfoById(cId, db, null);
        return list.stream().map(TableInfoDO::getTableName).distinct().collect(Collectors.toList());
    }

    @RequestMapping("/columns")
    public Object selectColumns(String cId, String db, String tname){
        List<TableInfoDO> list = sql.selectTableInfoById(cId, db, tname);
        return list;
    }

    @RequestMapping("/query")
    public void getMetaData(String db, String tnm) throws SQLException {
        SimpleDataSource source = new SimpleDataSource(com.github.abel533.database.Dialect.MYSQL, url, user, pwd);
        DBMetadataUtils utils = new DBMetadataUtils(source);
        DatabaseConfig config = new DatabaseConfig(db, null, tnm);
        List<IntrospectedTable> tableList = utils.introspectTables(config);
        DatabaseMetaData databaseMetaData = utils.getDatabaseMetaData();
        for (IntrospectedTable table : tableList) {
            System.out.println(table.getName() + ":");
            for (IntrospectedColumn column : table.getAllColumns()) {
                System.out.println(column.getName() + " - " +
                        column.getJdbcTypeName() + " - " +
                        column.getJavaProperty() + " - " +
                        column.getFullyQualifiedJavaType().getFullyQualifiedName() + " - " +
                        column.getRemarks());
            }
        }
    }

    @Data
    static class MyBean{
        private String id;
        private String city;
        private String code;
    }
}
