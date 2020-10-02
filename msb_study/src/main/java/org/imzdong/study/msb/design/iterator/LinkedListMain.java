package org.imzdong.study.msb.design.iterator;

public class LinkedListMain {

    public static void main(String[] args) {
        CustomCollection<String> linkedList = new CustomLinkedList<>();
        for (int i = 0; i < 16; i++) {
            linkedList.add("str link "+i);
        }
        System.out.println(linkedList.size());
        CustomIterator<String> iterator = linkedList.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
