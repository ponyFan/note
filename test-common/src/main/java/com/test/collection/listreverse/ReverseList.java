package com.test.collection.listreverse;

/**
 * @author zelei.fan
 * @date 2019/11/8 13:37
 */
public class ReverseList {

    public static void main(String[] args) {
        Node head = null;
        for (int i = 0; i < 10; i ++){
            Node node = new Node(i);
            if (null == head){
                head = node;
            }else {
                Node temp = head;
                while(temp.getNext() != null){
                    temp = temp.getNext();
                }
                temp.setNext(node);
            }
        }
        //[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        System.out.println(head);
        reverse0(head);
        Node node = reverse1(head);
        System.out.println("newnode : " + node);
    }

    /**
     * 递归方式
     * 1、递归到最底层，即value=9的时候开始返回
     * 2、返回的node对象就是head，然后将当前node依次放在最后面的next
     * @param node
     * @return
     */
    public static Node reverse1(Node node){
        if (node.getNext() == null){
            return node;
        }
        //从最后一个node（node的value为9时）开始设置next，每次遍历出链表中最后一个node，然后把next则为当前node
        Node newnode = reverse1(node.getNext());
        Node tmp = newnode;
        while (tmp.getNext() != null){
            tmp = tmp.getNext();
        }
        tmp.setNext(node);
        node.setNext(null);
        return newnode;
    }

    /**
     * 遍历方式
     * 1、从节点0开始遍历node
     * 2、将当前node的next对象缓存到next对象中
     * 3、将当前node换存到pre对象中，并设置其next为null
     * 4、设置pre对象为当前node的next，即把链表的结构反转了
     * 5、继续遍历next对象
     * @param node
     */
    public static void reverse0(Node node){
        Node pre = null;
        Node next = null;
        while (node != null){
            next = node.getNext();
            node.setNext(pre);
            pre = node;
            node = next;
        }
    }
}
