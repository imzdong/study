package org.imzdong.study.stone.lettcode.link;

/**
 * @author admin
 * @date 2021/4/29 10:21 上午
 */
public class NodeUtil {

    public static Node createLink(String head, int heap){
        Node node = new Node(head + heap);
        Node cur = node;
        while (heap > 0){
            heap--;
            cur.next = new Node(head+heap);
            cur = cur.next;
        }
        return node;
    }

    public static Node createCircleLink(String head, int heap){
        Node node = new Node(head + heap);
        Node cur = node;
        Node circle = null;
        while (heap > 0){
            heap--;
            cur.next = new Node(head+heap);
            cur = cur.next;
            if(heap == 3){
                circle = cur;
            }
            if(heap == 0){
                cur.next = circle;
            }
        }
        return node;
    }
}
