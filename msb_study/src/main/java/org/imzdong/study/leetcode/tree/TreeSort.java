package org.imzdong.study.leetcode.tree;

/**
 * @author zhoudong6
 * @since 2021年1月11日, 0011 13:24
 */
public class TreeSort {

    public static void main(String[] args) {
        /**
         * 前序：1,2,3,4,5,4,5,6,  3,4,5,6,5,6,7
         *                          1
         *                2                      3
         *           3        4              4          5
         *        4     5   5    6       5       6   6     7
         * 中序：4,3,2,1,3
         * 后序：
         */
        Node node1 = new Node("1");
        childNode(node1, 2, 1);
        preSort(node1);
        midSort(node1);
        afterSort(node1);
    }

    private static void childNode(Node node, int value, int number){
        if(number > 3){
            return;
        }
        number++;
        Node leftNode = new Node((value++)+"");
        childNode(leftNode, value, number);
        Node rightNode = new Node((value++)+"");
        childNode(rightNode, value, number);
        node.right = rightNode;
        node.left = leftNode;
    }

    /**
     * 前序打印：头→左→右
     * @param node
     */
    private static void preSort(Node node){
        if(node == null){
            return;
        }
        System.out.print(node.value+",");
        preSort(node.left);
        preSort(node.right);
    }

    /**
     * 中序打印：左→头→右
     * @param node
     */
    private static void midSort(Node node){
        if(node == null){
            return;
        }
        preSort(node.left);
        System.out.print(node.value+",");
        preSort(node.right);
    }

    /**
     * 后序打印：左→右→头
     * @param node
     */
    private static void afterSort(Node node){
        if(node == null){
            return;
        }
        preSort(node.left);
        preSort(node.right);
        System.out.print(node.value+",");
    }

    private static class Node{
        public String value;
        public Node left;
        public Node right;

        public Node(String value) {
            this.value = value;
        }

    }
}
