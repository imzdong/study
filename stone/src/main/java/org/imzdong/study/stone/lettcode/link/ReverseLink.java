package org.imzdong.study.stone.lettcode.link;

/**
 * 单链表反转
 * @author admin
 * @date 2021/4/27 8:19 下午
 */
public class ReverseLink {

    public static void main(String[] args) {
        ListNode listNode = NodeUtil.createLink(2);
        System.out.println(listNode);
        //Node reverse = threeReverse(node);
        ListNode reverse = twoReverse(listNode);
        System.out.println(reverse);
    }

    /**
     * 把当前链表的下一个节点pCur插入到头结点dummy的下一个节点中，就地反转。
     * dummy->1->2->3->4->5的就地反转过程：
     *
     * dummy->2->1->3->4->5
     * dummy->3->2->1->4->5
     * dummy->4>-3->2->1->5
     * dummy->5->4->3->2->1
     * @param listNode
     * @return
     */
    private static ListNode twoReverse(ListNode listNode){
        if(listNode == null || listNode.next == null){
            return listNode;
        }
        ListNode xn = new ListNode(1000);
        xn.next = listNode;
        ListNode next = listNode.next;
        //xn->1->2->3
        //node:1;next=2;node.next=3;xn.next=2;2.next=1;next=3;
        //xn->2->1->3
        //一定要注意链表边界问题，很容易丢失结点
        while (next != null){
            listNode.next = next.next;
            next.next = xn.next;
            xn.next = next;
            next = listNode.next;
        }
        return xn.next;
    }

    /**
     * 三个指针，每次三个指针都向下移动
     * @param head
     * @return
     */
    private static ListNode threeReverse(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head.next;
        while (true){
            cur.next = pre;
            if(next == null){
                break;
            }
            pre = cur;
            cur = next;
            next = cur.next;
        }
        return cur;
    }

}
