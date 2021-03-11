package org.imzdong.study.stone.lettcode.tree;

import java.util.LinkedList;

/**
 * @author zhoud
 * @since 2021/3/10 21:36
 */
public class TreeUtil {


    public static Node createTree(LinkedList<String> list){
        if(list == null || list.size()==0){
            return null;
        }
        String first = list.removeFirst();
        if(first != null && first.length() > 0){
            Node node = new Node(first);
            node.left = createTree(list);
            node.right = createTree(list);
            return node;
        }
        return null;
    }
}
