package com.test.collection.listreverse;

/**
 * @author zelei.fan
 * @date 2020/1/3 9:25
 * @description
 */
public class Test {

    public static void main(String[] args) {
        Node node = null;
        for (int i = 0; i < 10; i ++){
            Node node1 = new Node(i);
            if (i == 0){
                node = node1;
            }else {
                Node node2 = node;
                while (node2.getNext() != null){
                    node2 = node2.getNext();
                }
                node2.setNext(node1);
            }
        }
        /*reverse(node);*/
        /*System.out.println(node);*/

        Node node1 = reverse2(node);
        System.out.println(node1);
    }

    public static void reverse(Node node){
        Node tmp = node;
        Node node1 = null;
        while (tmp.getNext()!= null){
            Node node2 = new Node(tmp.getValue());
            if (null == node1){
                node1 = node2;
            }else {
                node2.setNext(node1);
                node1 = node2;
            }
            tmp = tmp.getNext();
        }
        Node node2 = new Node(tmp.getValue());
        node2.setNext(node1);
        System.out.println(node2);
    }

    public static Node reverse2(Node node){
        Node node1 = null;
        Node next = node.getNext();
        if (null != next){
            node1 = reverse2(next);
        }else {
            return node;
        }
        Node tmp = node1;
        while (tmp.getNext() != null){
            tmp = tmp.getNext();
        }
        node.setNext(null);
        tmp.setNext(node);
        return node1;
    }
}
