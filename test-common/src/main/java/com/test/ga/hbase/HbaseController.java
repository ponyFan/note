package com.test.ga.hbase;

import com.test.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hbase")
@Api(value = "habse controller test")
public class HbaseController {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    private String family = "cf";

    @PostMapping("/create")
    @ApiOperation(value = "create hbase table")
    public void createTable(String tableName){
        try {
            hbaseTemplate.createTable(tableName, family);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/insert")
    @ApiOperation(value = "insert data to hbase")
    public void insertTable(@RequestParam(value = "tableName") String tableName, @RequestBody Student stu){
        Put put = new Put(Bytes.toBytes(stu.getId()));
        put.addColumn(family.getBytes(), "age".getBytes(), Bytes.toBytes(stu.getAge()));
        put.addColumn(family.getBytes(), "name".getBytes(), stu.getName().getBytes());
        Connection connection = hbaseTemplate.getConnection();
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "update data to hbase")
    public void updateTable(@RequestParam(value = "tableName") String tableName, @RequestBody Student stu){
        Put put = new Put(Bytes.toBytes(stu.getId()));
        put.addColumn(family.getBytes(), "age".getBytes(), Bytes.toBytes(stu.getAge()));
        put.addColumn(family.getBytes(), "name".getBytes(), stu.getName().getBytes());
        Connection connection = hbaseTemplate.getConnection();
        try {
            Table table = connection.getTable(TableName.valueOf(tableName));
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/queryByRowKey")
    @ApiOperation(value = "query by rowKey")
    public Map<String, String> getDataByRowKey(String tableName, String rowKey){
        try {
            return hbaseTemplate.getDataByRowKey(tableName, rowKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/queryCell")
    @ApiOperation(value = "get cell data")
    public String getCellData(String tableName, String rowKey, String family, String col){
        try {
            return hbaseTemplate.getCellData(tableName, rowKey, family, col);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/queryTable")
    @ApiOperation(value = "get table data")
    public List<Map<String, String>> getAllData(String tableName){
        try {
            return hbaseTemplate.getAllData(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/deleteByRowKey")
    @ApiOperation(value = "delete data by rowkey")
    public void deleteByRowKey(String tableName, String rowKey){
        try {
            hbaseTemplate.deleteByRowKey(tableName, rowKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/deleteCell")
    @ApiOperation(value = "delete cell")
    public void deleteCell(String tableName, String rowKey, String family, String cell){
        try {
            hbaseTemplate.deleteCell(tableName, rowKey, family, cell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
