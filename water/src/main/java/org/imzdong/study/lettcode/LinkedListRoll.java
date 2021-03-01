package org.imzdong.study.lettcode;

/**
 * LeetCode 第 25 题：给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 给定这个链表：1->2->3->4->5
 * 当 k=2 时，应当返回：2->1->4->3->5
 * 当 k=3 时，应当返回：3->2->1->4->5
 * @author zhoudong6
 * @since 2021年3月1日, 0001 10:57
 */
public class LinkedListRoll {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        ListNode next = head.next;
        boolean flag = true;
        int num = 3;
        while (flag){
            next.next = new ListNode(num++);
            next = next.next;
            if(num>8){
                flag = false;
            }
        }
        System.out.println(head);
        head = reverse(head);
        System.out.println(head);
    }

    private static ListNode reverse(ListNode head) {
        //1 2 3 4 5 6
        if (head != null) {
            ListNode next = head.next;
            head.next = null;
            next = reverse(next);
            if(next != null) {
                next.next = head;
                head = next;
            }
        }
        return head;
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }
}
