package org.imzdong.study.stone.lettcode.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树层级遍历（广度优先遍历）
 * @author zhoud
 * @since 2021/3/11 11:42
 */
public class TreeHierarchySort {

    public static void main(String[] args) {
        String [] trees =  {"1","2","4",null,null,"5",null,null,
                "3","6",null,null,"7",null,null};
        List<String> list = Arrays.asList(trees);
        Node tree = TreeUtil.createTree(new LinkedList<>(list));
        treeSort(tree);
    }

    private static void treeSort(Node head){
        Queue<Node> queue = new LinkedList<>();
        queue.offer(head);
        while (!queue.isEmpty()){
            Node poll = queue.poll();
            System.out.print(poll.value);
            if(poll.left != null){
                queue.offer(poll.left);
            }
            if(poll.right != null){
                queue.offer(poll.right);
            }
        }
    }
}
