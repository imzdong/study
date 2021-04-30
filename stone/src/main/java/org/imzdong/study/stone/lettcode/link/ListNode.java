package org.imzdong.study.stone.lettcode.link;

/**
 * @author admin
 * @date 2021/4/28 7:42 下午
 */
public class ListNode {

    int val;
    ListNode next;

    public ListNode(int value) {
        this.val = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "val='" + val + '\'' +
                ", next=" + next +
                '}';
    }

}
