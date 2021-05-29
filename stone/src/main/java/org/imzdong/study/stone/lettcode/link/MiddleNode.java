package org.imzdong.study.stone.lettcode.link;

/**
 * @author admin
 * @date 2021/5/8 8:15 下午
 */
public class MiddleNode {

    public static void main(String[] args) {

    }

    private static ListNode middleNode(ListNode head){
        ListNode dummary = new ListNode(-1);
        dummary.next = head;
        ListNode first = dummary;
        ListNode second = dummary.next;
        while (true){
            //[1,2,3,4,5]
            first = first.next;
            if(second == null || second.next == null){
                return first;
            }
            second = second.next.next;
        }
    }
}
