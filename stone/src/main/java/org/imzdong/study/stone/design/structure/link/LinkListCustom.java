package org.imzdong.study.stone.design.structure.link;

/**
 *
 */
public class LinkListCustom {

    private Node first;
    private int length;

    public LinkListCustom() {
    }

    /**
     * 末尾插入
     * @param value
     */
    public void addTail(int value){
        Node indexNode = new Node(value, null);
        if(first == null){
            first = indexNode;
        }else {
            Node last = first;
            while (last.next!=null){
                last = last.next;
            }
            last.next = indexNode;
        }
        length++;
    }

    /**
     * 末尾插入
     * @param value
     */
    public void addByNode(Node node,int value){
        Node newNode = new Node(value, node.next);
        node.next = newNode;
        length++;
    }

    /**
     * 查找
     * @param index
     */
    public int get(int index){
        if(first == null){
            return -1;
        }else {
            Node last = first;
            int pos = 0;
            while (last.next!=null && pos++!=index){
                last = last.next;
            }
            return last.data;
        }
    }


    /**
     * 链表存储结构
     */
    private static class Node{

        int data;
        Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "LinkListCustom{" +
                "first=" + first +
                '}';
    }
}
