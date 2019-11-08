package com.test.collection.listreverse;

import lombok.Data;
import lombok.ToString;

/**
 * @author zelei.fan
 * @date 2019/11/8 13:54
 */
@Data
@ToString
public class Node {

    private int value;

    private Node next;

    Node(int value){
        this.value = value;
    }
}
