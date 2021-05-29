package org.imzdong.study.stone.lettcode.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author zhoud
 * @since 2021/3/11 7:36
 */
public class TreeSortByStack {

    public static void main(String[] args) {
        String [] trees =  {"1","2","4",null,"8",null,null,"5",null,null,
                "3","6",null,null,"7",null,null};
        Node tree = TreeUtil.createTree(new LinkedList<>(Arrays.asList(trees)));
        preSortByStack(tree);
    }

    private static void preSortByStack(Node head) {
        Stack<Node> nodes = new Stack<>();
        while (head != null || !nodes.isEmpty()){
            while (head != null){
                System.out.println(head.value);
                nodes.add(head);
                head = head.left;
            }
            if(!nodes.isEmpty()){
                head = nodes.pop();
                head = head.right;
            }
        }
    }
}
