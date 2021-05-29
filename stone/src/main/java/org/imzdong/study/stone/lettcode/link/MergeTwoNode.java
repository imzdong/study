package org.imzdong.study.stone.lettcode.link;

/**
 * 两个有序的链表合并
 * @author admin
 * @date 2021/4/30 2:03 下午
 */
public class MergeTwoNode {

    public static void main(String[] args) {
        mergeTwoLists(null, null);
    }

    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null){
            return null;
        }
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode l3 = new ListNode(0);
        ListNode rr = l3;
        while (true){
            if(l1.val <= l2.val){
                l3.next = l1;
                l1 = l1.next;
                if(l1 == null){
                    l3.next.next = l2;
                }
            }else {
                l3.next = l2;
                l2 = l2.next;
                if(l2 == null){
                    l3.next.next = l1;
                }
            }
            if(l1 == null || l2 == null){
                break;
            }
            l3 = l3.next;
        }
        return rr.next;
    }

    /**
     * 上述思路也可以通过递归来实现。
     * 从两个输入链表的头结点l1、l2开始进行比较，将结点值较小者设为头结点，
     * 递归调用自身计算l1.next和l2(或l1和l2.next)的合并，使头节点指向合并后的链表头。
     * @param l1
     * @param l2
     * @return
     */
    private static ListNode recurseMerge(ListNode l1, ListNode l2){
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode head;
        if(l1.val <= l2.val){
            head = l1;
            head.next = recurseMerge(l1.next, l2);
        }else {
            head = l2;
            head.next = recurseMerge(l2.next, l1);
        }
        return head;
    }
}
