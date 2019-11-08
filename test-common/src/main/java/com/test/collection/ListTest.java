package com.test.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zelei.fan
 * @date 2019/10/13 11:28
 */
public class ListTest {

    public static List<TestModel> assembleArrayList(int size){
        int num = size;
        long l = System.currentTimeMillis();
        List<TestModel> arrayList = new ArrayList<>();
        while (size > 0){
            arrayList.add(new TestModel(size, "test1"));
            size--;
        }
        long l1 = System.currentTimeMillis();
        System.out.println("ArrayList add " + num + " spend " + (l1-l) + "ms");
        return arrayList;
    }

    public static List<TestModel> assembleLinkedList(int size){
        int num = size;
        long l = System.currentTimeMillis();
        List<TestModel> linkedList = new LinkedList<>();
        while (size > 0){
            linkedList.add(new TestModel(size, "test2"));
            size--;
        }
        long l1 = System.currentTimeMillis();
        System.out.println("LinkedList add " + num + " spend " + (l1-l) + "ms");
        return linkedList;
    }

    public static void main(String[] args) {
        int size = 100000;
        List<TestModel> arrayList = assembleArrayList(size);
        //arraylist查询能力强，因为下标索引；新增能力其实也不差（顺序写的情况下），顺序写每次是添加最后面
        long l = System.currentTimeMillis();
        for (int i = 0; i < size; i ++){
            arrayList.get(i);
        }
        long l1 = System.currentTimeMillis();
        System.out.println("ArrayList get "+ size + " spend " + (l1-l) + "ms");

        //linkedlist查询能力差，每次查询都要重新遍历
        List<TestModel> linkedList = assembleLinkedList(size);
        long l2 = System.currentTimeMillis();
        for (int i = 0; i < size; i ++){
            linkedList.get(i);
        }
        long l3 = System.currentTimeMillis();
        System.out.println("LinkedList get "+ size + " spend " + (l3-l2) + "ms");
    }
}
