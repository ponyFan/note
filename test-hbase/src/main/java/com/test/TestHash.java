package com.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;

import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

/**
 * @author zelei.fan
 * @date 2019/9/16 17:10
 */
public class TestHash {
    // 随机取机数目 实验基数
    private int baseRecord;
    // rowkey生成器
    private RowKeyGenerator rkGen;
    // 取样时，由取样数目及region数相除所得的数量.
    private int splitKeysBase;
    // splitkeys个数
    private int splitKeysNumber;
    // 由抽样计算出来的splitkeys结果
    private byte[][] splitKeys;

    public TestHash(int baseRecord, int prepareRegions) {
        this.baseRecord = baseRecord;
        // 实例化rowkey生成器
        rkGen = new HashRowKeyGenerator();
        // region数量减一
        splitKeysNumber = prepareRegions - 1;
        // 抽样基数 除以 region数量
        splitKeysBase = baseRecord / prepareRegions;
    }

    public byte[][] calcSplitKeys() {
        splitKeys = new byte[splitKeysNumber][]; // new byte[9][]
        // 使用treeset保存抽样数据，已排序过
        TreeSet<byte[]> rows = new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);
        // 把生成的散列byte[] 添加到rows
        for (int i = 0; i < baseRecord; i++) {
            rows.add(rkGen.nextId());
        }
        int pointer = 0;
        Iterator<byte[]> rowKeyIter = rows.iterator();
        int index = 0;
        while (rowKeyIter.hasNext()) {
            byte[] tempRow = rowKeyIter.next();
            rowKeyIter.remove();
            if ((pointer != 0) && (pointer % splitKeysBase == 0)) {
                if (index < splitKeysNumber) {
                    splitKeys[index] = tempRow;
                    index++;
                }
            }
            pointer++;
        }
        rows.clear();
        rows = null;
        return splitKeys;
    }

    // interface
    public interface RowKeyGenerator {
        byte[] nextId();
    }

    // implements
    public class HashRowKeyGenerator implements RowKeyGenerator {
        private long currentId = 1;
        private long currentTime = System.currentTimeMillis();
        private Random random = new Random();

        public byte[] nextId() {
            try {
                currentTime += random.nextInt(1000);
                byte[] lowT = Bytes.copy(Bytes.toBytes(currentTime), 4, 4);
                byte[] lowU = Bytes.copy(Bytes.toBytes(currentId), 4, 4);
                return Bytes.add(MD5Hash.getMD5AsHex(Bytes.add(lowU, lowT))
                        .substring(0, 8).getBytes(), Bytes.toBytes(currentId));
            } finally {
                currentId++;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        TestHash worker = new TestHash(1000, 20);
        byte[][] splitKeys = worker.calcSplitKeys();
        String namespace = "hbase_api";
        String tbname = "hbase_hashrowkey2";
        String colfamily = "info";

        // 读取配置信息
        Configuration conf = HBaseConfiguration.create();

        conf.set("hbase.zookeeper.quorum", "hadoop02,hadoop01,hadoop03");
        conf.set("hbase.zookeeper.property.clientPort","2181");

        HBaseAdmin admin = new HBaseAdmin(conf);
        TableName tableName = TableName.valueOf(tbname);

        if (admin.tableExists(tableName)) {
            try {
                admin.disableTable(tableName);
            } catch (Exception e) {
            }
            admin.deleteTable(tableName);
        }

        HTableDescriptor tableDesc = new HTableDescriptor(tableName);
        HColumnDescriptor columnDesc = new HColumnDescriptor(
                Bytes.toBytes(colfamily));
        columnDesc.setMaxVersions(1);
        tableDesc.addFamily(columnDesc);

        admin.createTable(tableDesc, splitKeys);

        admin.close();
    }
}
