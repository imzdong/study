package org.imzdong.study.stone.lettcode.link;

/**
 * 链表中环的检测
 * @author admin
 * @date 2021/4/28 5:15 下午
 */
public class CircleLink {

    public static void main(String[] args) {
        ListNode circle = NodeUtil.createCircleLink( 5);
        boolean b = checkCircleLink(circle);
        System.out.println(b);
    }

    private static boolean checkCircleLink(ListNode listNode){
        ListNode slow = listNode;
        ListNode quick = listNode.next;
        while (quick!=null){
            if(slow.equals(quick)){
                return true;
            }
            slow = slow.next;
            if(quick.next == null){
                return false;
            }
            quick = quick.next.next;
        }
        return false;
    }
}
