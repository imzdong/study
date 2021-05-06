package org.imzdong.study.stone.lettcode.link;

/**
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * @author admin
 * @date 2021/4/30 5:45 下午
 */
public class RemoveNthFromEnd {

    public static void main(String[] args) {
        ListNode circleLink = NodeUtil.createCircleLink(5);
        ListNode listNode = removeNthFromEnd(circleLink, 2);
        System.out.println(listNode);
    }

    private static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        int length = 0;
        while (first != null){
            length++;
            first = first.next;
        }
        ListNode dummary = new ListNode(0);
        dummary.next = head;
        ListNode delPre = dummary;
        for (int x=0;x<length-n;x++){
            delPre = delPre.next;
        }
        delPre.next = delPre.next.next;
        return dummary.next;
    }
}
