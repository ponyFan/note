package com.test.collection;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * @author zelei.fan
 * @date 2020/6/22 15:37
 * @description
 */
public class MapTest {

    public static void main(String[] args) {
        System.out.println(1<<30);
        /*
        * 默认容量16（1<<4, 即2的4次方）
        * 最大容量为1073741824(1<<30, 即2的30次方)
        * 负载因子0.75f, 当数据量达到当前容量的75%时，hashMap会进行扩容；如初始化容量10，当插入到第八个key时就会触发扩容
        * 一个桶的树化阈值默认8，即一个桶中元素超过8时则转为红黑树
        * 树还原成桶阈值默认6，当元素个数小于6时转成列表
        * hash表容量默认64，即当hash桶超过64时才会进行树化，否则只是扩容；超过64时进行树化
        * */
        long l = System.currentTimeMillis();
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            hashMap.put(i, 1);
        }
        long l1 = System.currentTimeMillis();
        System.out.println("spend time : " + (l1-l) + "ms");
        hashMap.get(1);

        LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, 1);
        linkedHashMap.get(1);

        Hashtable<Object, Object> hashtable = new Hashtable<>();
        hashtable.put(1, 1);
        hashtable.get(1);

        TreeMap<Object, Object> treeMap = new TreeMap<>();
        treeMap.put(1, 1);
        treeMap.get(1);

    }
}
