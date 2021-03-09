package org.imzdong.study.stone.lettcode.tree;

import java.util.LinkedList;

/**
 * @author zhoud
 * @since 2021/3/9 10:38
 */
public class TreeSort {

    public static void main(String[] args) {
        Node node = new Node(0);
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            list.add(i);
        }
        //createTreeByLR(node, list);
        Node tree = createTree(list);
        preSort(tree);
    }

    private static void preSort(Node head){
        if (head == null){
            return;
        }
        System.out.println(head.value);
        preSort(head.left);
        preSort(head.right);
    }

    private static Node createTree(LinkedList<Integer> list){
        if(list.size()<=0){
            return null;
        }
        Integer first = list.removeFirst();
        if(first != null) {
            Node node = new Node(first);
            node.left = createTree(list);
            node.right = createTree(list);
            return node;
        }
        return null;

    }

    private static void createTreeByLR(Node node, LinkedList<Integer> list){
        if(list.size() <= 0){
            return;
        }
        Node leftTree = createLeftTree(node, list.removeFirst());
        Node rightTree = createRightTree(node, list.removeFirst());
        createTreeByLR(leftTree, list);
        createTreeByLR(rightTree, list);
    }

    private static Node createLeftTree(Node node, int value){
        node.left = new Node(value);
        return node.left;
    }

    private static Node createRightTree(Node node, int value){
        node.right = new Node(value);
        return node.right;
    }

    private static class Node{
        public Node(int value){
            this.value = value;
        }

        int value;
        Node left;
        Node right;
    }
}
