package org.imzdong.study.stone.lettcode.link;

/**
 * @author admin
 * @date 2021/4/28 7:42 下午
 */
public class Node {

    String value;
    Node next;

    public Node(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' +
                ", next=" + next +
                '}';
    }

}
