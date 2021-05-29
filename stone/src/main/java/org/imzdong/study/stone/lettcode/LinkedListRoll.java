package org.imzdong.study.stone.lettcode;

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
        ListNode head = createListNode(5, 0);
        System.out.println(head);
        //head = reverseRecursion(head);
        head = reversePointer(head);
        System.out.println(head);
    }

    private static ListNode reversePointer(ListNode head) {
        //1 2 3 4 5
        if(head == null || head.next == null){
            return head;
        }
        ListNode current = head;
        ListNode pre = null;
        ListNode next = head.next;
        while (next != null){
            ListNode temp = next.next;
            current.next = pre;
            next.next = current;
            pre = current;
            current = next;
            next = temp;
        }
        return current;
    }

    /**
     * 生成链表
     * @param length 链表长度
     * @param headValue 头节点值
     * @return 头节点
     */
    private static ListNode createListNode(int length, int headValue){
        ListNode head = new ListNode(headValue);
        ListNode temp = head;
        boolean flag = true;
        while (flag){
            temp.next = new ListNode(++headValue);
            temp = temp.next;
            if(headValue > length){
                flag = false;
            }
        }
        return head;
    }

    /**
     *  1 head=1 reverse = reverse(2) = 5 2.next=1 1.next=null
     *  2 head=2 reverse = reverse(3) = 5 3.next=2 2.next=null
     *  3 head=3 reverse = reverse(4) = 5 4.next=3 3.next=null
     *  4 head=4 reverse = reverse(5) = 5 5.next=4 4.next=null
     *  5 head=5 return 5
     *  递归调用
     * @param head 链表头节点
     * @return ListNode 头节点
     */
    private static ListNode reverseRecursion(ListNode head) {
        //1 2 3 4 5
        if(head == null || head.next == null){
            return head;
        }
        ListNode reverse = reverseRecursion(head.next);
        head.next.next = head;
        head.next = null;
        return reverse;
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
