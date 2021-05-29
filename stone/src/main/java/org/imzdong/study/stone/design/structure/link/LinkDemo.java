package org.imzdong.study.stone.design.structure.link;

/**
 * @description:
 * @author: Winter
 * @time: 2020年4月27日, 0027 13:23
 */
public class LinkDemo {

    public static void main(String[] args) {
        LinkListCustom linkListCustom = new LinkListCustom();
        System.out.println(linkListCustom);
        linkListCustom.addTail(1);
        linkListCustom.addTail(2);
        linkListCustom.addTail(3);
        linkListCustom.addTail(4);
        System.out.println(linkListCustom);
        System.out.println("-----------");
        System.out.println(linkListCustom.get(2));

        /*List<String> list = new LinkedList<>();
        list.get(1);*/
    }

}
