package org.imzdong.study.msb.design.iterator;

public class ArrayMain {

    public static void main(String[] args) {
        CustomCollection<String> list = new CustomArrayList<>();
        for (int i = 0; i < 28; i++) {
            list.add("str:"+i);
        }
        System.out.println(list.size());
        CustomIterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            //System.out.println(next);
        }
    }
}
