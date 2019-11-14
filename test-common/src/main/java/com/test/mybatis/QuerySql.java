package com.test.mybatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zelei.fan
 * @date 2019/11/12 9:52
 */
@Mapper
public interface QuerySql{

    @Select({"<script>select * from testDB.city_code limit 2</script>"})
    List<MysqlTest.MyBean> select();

    @Select("select * from connection_info where id = #{id} and deleted = 0")
    ConnectionDO selectConnectionInfoById(String id);

    @Select("select * from connection_info")
    List<ConnectionDO> selectConnection();

    @Delete({"<script>delete from table_info where 1=1" +
            "<when test='cId!=null and cId!=\"\"'> " +
            "and connection_id = #{cId}" +
            "</when>" +
            "<when test='db!=null and db!=\"\"'> " +
            "and db = #{db}" +
            "</when>" +
            "<when test='tname!=null and tname!=\"\"'> " +
            "and table_name = #{tname}" +
            "</when>" +
            "</script>"})
    void delete(@Param("cId") String cId, @Param("db") String db, @Param("tname") String tname);

    @Select({"<script>" +
            "insert into table_info (id, connection_id, db, table_name, column_name, column_type, remark) values " +
            "<foreach collection='tables' item='t' separator=','> " +
            "(#{t.id}, #{t.connectionId}, #{t.db}, #{t.tableName}, #{t.columnName}, #{t.columnType}, #{t.remark})" +
            "</foreach> " +
            "</script>"})
    void insertTables(@Param("tables") List<TableInfoDO> tables);

    @Select({"<script>" +
            "select distinct(db) from table_info where 1=1" +
            "<when test='cId!=null and cId!=\"\"'> " +
            "and connection_id = #{cId}" +
            "</when>" +
            "</script>"})
    List<String> selectByCid(String cid);

    @Select({"<script>" +
            "select * from table_info where 1=1" +
            "<when test='cId!=null and cId!=\"\"'> " +
            "and connection_id = #{cId}" +
            "</when>" +
            "<when test='db!=null and db!=\"\"'> " +
            "and db = #{db}" +
            "</when>" +
            "<when test='tname!=null and tname!=\"\"'> " +
            "and table_name = #{tname}" +
            "</when>" +
            "</script>"})
    List<TableInfoDO> selectTableInfoById(@Param("cId") String cId, @Param("db") String db, @Param("tname") String tname);
}
