package org.imzdong.study.stone.lettcode.link;

/**
 * @author admin
 * @date 2021/4/29 10:21 上午
 */
public class NodeUtil {

    public static ListNode createLink(int heap){
        ListNode listNode = new ListNode(heap);
        ListNode cur = listNode;
        while (heap > 0){
            heap--;
            cur.next = new ListNode(heap);
            cur = cur.next;
        }
        return listNode;
    }

    public static ListNode createCircleLink(int heap){
        ListNode listNode = new ListNode(heap);
        ListNode cur = listNode;
        ListNode circle = null;
        while (heap > 0){
            heap--;
            cur.next = new ListNode(heap);
            cur = cur.next;
            if(heap == 3){
                circle = cur;
            }
            if(heap == 0){
                cur.next = circle;
            }
        }
        return listNode;
    }
}
