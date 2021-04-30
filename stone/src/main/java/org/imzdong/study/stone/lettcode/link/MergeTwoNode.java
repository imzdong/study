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
}
