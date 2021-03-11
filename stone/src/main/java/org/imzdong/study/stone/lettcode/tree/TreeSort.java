package org.imzdong.study.stone.lettcode.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhoud
 * @since 2021/3/9 10:38
 */
public class TreeSort {

    public static void main(String[] args) {
        String [] trees =  {"1","2","4",null,null,"5",null,null,
                "3","6",null,null,"7",null,null};
        //createTreeByLR(node, list);
        List<String> list = Arrays.asList(trees);
        Node tree = TreeUtil.createTree(new LinkedList<>(list));
        preSort(tree);
        System.out.println("-------------");
        middleSort(tree);
        System.out.println("-------------");
        afterSort(tree);
    }

    private static void preSort(Node head){
        if (head == null){
            return;
        }
        System.out.println(head.value);
        preSort(head.left);
        preSort(head.right);
    }

    private static void middleSort(Node head){
        if (head == null){
            return;
        }
        middleSort(head.left);
        System.out.println(head.value);
        middleSort(head.right);
    }

    private static void afterSort(Node head){
        if (head == null){
            return;
        }
        afterSort(head.left);
        afterSort(head.right);
        System.out.println(head.value);
    }

}
