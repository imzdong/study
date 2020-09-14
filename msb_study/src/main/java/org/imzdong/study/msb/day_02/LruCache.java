package org.imzdong.study.msb.day_02;

import java.util.HashMap;
import java.util.Map;

/**
 * 首先构造函数接受一个capacity参数作为缓存的最大容量，然后实现两个API： *
 * 一个是 put(key, val) 方法插入新的或更新已有键值对，如果缓存已满的话，要删除那个最久没用过的键值对以腾出位置插入。 *
 * 另一个是 get(key) 方法获取 key 对应的 val，如果 key 不存在则返回 -1。
 */
public class LruCache {

    private Map<String,Node> map;
    private int capacity;
    private int size = 0;
    private Node head;
    private Node tail;

    public LruCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
    }

    private String get(String key){
        if(map.containsKey(key)){
            Node node = map.get(key);
            refresh(node);
            return node.val;
        }
        return null;
    }

    private void refresh(Node node){
        if(node.pre != null){
            //非头节点
            Node next= node.next;
            node.next = head;
            head = node;
            if(next != null){
                //非尾节点
                node.pre.next = next;
                next.pre = node.pre;
            }
        }//头节点
    }

    private void put(String key,String val){
        if(head == null){
            Node node = new Node(key, val);
            head = node;
            map.put(key,node);
            size++;
            return;
        }
        if(map.containsKey(key)){
            Node node = map.get(key);
            node.val = val;
            refresh(node);
        }else {
            if(size<capacity){
                Node node = new Node(key, val);
                head.pre = node;
                node.next = head;
                head = node;
                size++;
            }else {
                if(size == capacity){

                }
            }
        }
    }

    class Node{
        String key;
        String val;
        Node pre;
        Node next;

        public Node(String key, String val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return "(" + key  + ", " + val + ')';
        }
    }
}
