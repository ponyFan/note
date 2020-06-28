package com.test.ga.hbase;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zelei.fan
 * @date 2019/11/21 09:45
 */
@Component
public class HbaseTemplate {

    @Value("${hbase.quorum}")
    private String quorum;

    @Value("${hbase.master}")
    private String master;

    @Value("${hbase.zookeeper.port}")
    private String zkPort;

    private Connection connection;

    private static final String COLUMN = "column";
    private static final String ROW_KEY = "rowKey";
    private static final String COLUMN_NAME = "columnName";

    @PostConstruct
    public void initConfig() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", zkPort);
        configuration.set("hbase.zookeeper.quorum", quorum);
        configuration.set("hbase.master", master);
        this.connection = ConnectionFactory.createConnection(configuration);
    }

    public Connection getConnection(){
        return this.connection;
    }

    /**
     * 创建表
     * @param tableName 表名
     * @param cols 列族
     * @return
     */
    public int createTable(String tableName, String[] cols) throws IOException {
        TableName table = TableName.valueOf(tableName);
        Admin admin = connection.getAdmin();
        if (admin.tableExists(table)){
            return NumberUtils.BYTE_ZERO;
        }else {
            TableDescriptorBuilder tdc = TableDescriptorBuilder.newBuilder(table);
            for (String col : cols) {
                ColumnFamilyDescriptorBuilder cdb = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(col));
                ColumnFamilyDescriptor familyDescriptor = cdb.build();
                tdc.setColumnFamily(familyDescriptor);
            }
            admin.createTable(tdc.build());
            return NumberUtils.BYTE_ONE;
        }
    }

    /**
     * 创建表，单个列族
     * @param tableName
     * @param col
     * @return
     */
    public int createTable(String tableName, String col) throws IOException {
        return createTable(tableName, new String[]{col});
    }

    /**
     * 根据rowkey获取某条记录
     * @param tableName
     * @param rowKey
     * @return
     */
    public Map<String, String> getDataByRowKey(String tableName, String rowKey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        HashMap<String, String> map = Maps.newHashMap();
        map.put(ROW_KEY, rowKey);
        if (!get.isCheckExistenceOnly()){
            Result result = table.get(get);
            for (Cell cell : result.rawCells()) {
                String colName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                map.put(colName, value);
            }
        }
        return map;
    }

    /**
     * 查询指定cell内容
     * @param tableName
     * @param rowKey
     * @param family
     * @param col
     * @return
     */
    public String getCellData(String tableName, String rowKey, String family, String col) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        if (!get.isCheckExistenceOnly()){
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(col));
            Result result = table.get(get);
            byte[] resByte = result.getValue(Bytes.toBytes(family), Bytes.toBytes(col));
            return Bytes.toString(resByte);
        }
        return null;
    }

    /**
     * 获取表中所有信息
     * @param tableName
     * @return
     */
    public List<Map<String, String>> getAllData(String tableName) throws IOException {
        List<Map<String, String>> list = new ArrayList<>();
        Table table = connection.getTable(TableName.valueOf(tableName));
        ResultScanner results = table.getScanner(new Scan());
        for (Result result : results){
            Map map = Maps.newHashMap();
            map.put(ROW_KEY, new String(result.getRow()));
            for(Cell cell : result.rawCells()){
                String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                map.put(colName, value);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 根据rowkey删除
     * @param tableName
     * @param rowKey
     */
    public void deleteByRowKey(String tableName, String rowKey) throws IOException {
        deleteCell(tableName, rowKey, null, null);
    }

    /**
     * 删除某一列
     * @param tableName
     * @param rowKey
     * @param family
     * @param cell
     */
    public void deleteCell(String tableName, String rowKey, String family, String cell) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        if (null != family && null != cell){
            delete.addColumn(Bytes.toBytes(family), Bytes.toBytes(cell));
        }
        table.delete(delete);
    }

    /**
     * 删除表
     * @param tableName
     * @throws IOException
     */
    public void deleteTable(String tableName) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Admin admin = connection.getAdmin();
        admin.disableTable(name);
        admin.deleteTable(name);
    }

    /**
     * 根据rowkey过滤
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getByRow(String tableName, String rowKey) throws IOException {
        return filter(tableName, rowKey, null, null, null, HbaseFilterEnum.ROW);
    }

    /**
     * 根据family过滤
     * @param tableName
     * @param family
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getByFamily(String tableName, String family) throws IOException {
        return filter(tableName, null, family, null, null, HbaseFilterEnum.FAMILY);
    }

    /**
     * 根据列过滤
     * @param tableName
     * @param column
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getQualifier(String tableName, String column) throws IOException {
        return filter(tableName, null, null, column, null, HbaseFilterEnum.QUALIFIER);
    }

    /**
     * 根据值过滤
     * @param tableName
     * @param value
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getValue(String tableName, String value) throws IOException {
        return filter(tableName, null, null, null, value, HbaseFilterEnum.VALUE);
    }

    /**
     * 根据单列过滤
     * @param tableName
     * @param family
     * @param column
     * @param value
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getSingleCol(String tableName, String family, String column, String value) throws IOException {
        return filter(tableName, null, family, column, value, HbaseFilterEnum.SINGLE_COL);
    }

    /**
     * 单利排除过滤
     * @param tableName
     * @param family
     * @param column
     * @param value
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getSingleColExclude(String tableName, String family, String column, String value) throws IOException {
        return filter(tableName, null, family, column, value, HbaseFilterEnum.SINGLE_COL_EXCLUDE);
    }

    /**
     * 根据前缀过滤
     * @param tableName
     * @param prefix
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getPrefix(String tableName, String prefix) throws IOException {
        return filter(tableName, prefix, null, null, null, HbaseFilterEnum.ROW_PREFIX);
    }

    /**
     * 根据列前缀过滤
     * @param tableName
     * @param colPrefix
     * @return
     * @throws IOException
     */
    public List<Map<String, String>> getColPrefix(String tableName, String colPrefix) throws IOException {
        return filter(tableName, colPrefix, null, null, null, HbaseFilterEnum.COLUMN_PREFIX);
    }

    /**
     * 过滤
     * @param tableName
     * @param rowKey
     * @param family
     * @param column
     * @param value
     * @param type
     * @return
     */
    private List<Map<String, String>> filter(String tableName, String rowKey, String family, String column, String value,
                                             HbaseFilterEnum type) throws IOException{
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        switch (type){
            case ROW:
                RowFilter rowFilter = new RowFilter(CompareOperator.EQUAL, new BinaryComparator(rowKey.getBytes()));
                scan.setFilter(rowFilter);
                break;
            case FAMILY:
                FamilyFilter familyFilter = new FamilyFilter(CompareOperator.EQUAL, new BinaryComparator(family.getBytes()));
                scan.setFilter(familyFilter);
                break;
            case QUALIFIER:
                QualifierFilter qualifierFilter = new QualifierFilter(CompareOperator.EQUAL, new BinaryComparator(column.getBytes()));
                scan.setFilter(qualifierFilter);
                break;
            case VALUE:
                ValueFilter valueFilter = new ValueFilter(CompareOperator.EQUAL, new SubstringComparator(value));
                scan.setFilter(valueFilter);
                break;
            case SINGLE_COL:
                SingleColumnValueFilter singleFilter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(column), CompareOperator.EQUAL, Bytes.toBytes(value));
                scan.setFilter(singleFilter);
                break;
            case SINGLE_COL_EXCLUDE:
                SingleColumnValueExcludeFilter excludeFilter = new SingleColumnValueExcludeFilter(Bytes.toBytes(family), Bytes.toBytes(column), CompareOperator.EQUAL, Bytes.toBytes(value));
                scan.setFilter(excludeFilter);
                break;
            case ROW_PREFIX:
                PrefixFilter prefixFilter = new PrefixFilter(Bytes.toBytes(rowKey));
                scan.setFilter(prefixFilter);
                break;
            case COLUMN_PREFIX:
                ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(column.getBytes());
                scan.setFilter(columnPrefixFilter);
                break;
        }
        ResultScanner scanner = table.getScanner(scan);
        List<Map<String, String>> list = Lists.newArrayList();
        for (Result result : scanner) {
            String row = Bytes.toString(result.getRow());
            Cell[] cells = result.rawCells();
            HashMap<String, String> map = Maps.newHashMap();
            map.put(ROW_KEY, row);
            for (Cell cell : cells) {
                String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value1 = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                map.put(colName, value1);
            }
            list.add(map);
        }
        return list;
    }
}
