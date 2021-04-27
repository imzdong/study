package org.imzdong.study.stone.lettcode.link;

/**
 * 单链表反转
 * @author admin
 * @date 2021/4/27 8:19 下午
 */
public class ReverseLink {

    public static void main(String[] args) {
        Node node = createLink("node", 4);
        System.out.println(node);
    }

    private static Node reverse(Node head){
        return head;
    }

    private static Node createLink(String head, int heap){
        Node node = new Node(head + heap);
        Node cur = node;
        while (heap > 0){
            heap--;
            cur.next = new Node(head+heap);
            cur = cur.next;
        }
        return node;
    }

    private static class Node{
        String value;
        Node next;

        public Node(String value) {
            this.value = value;
        }
    }
}
