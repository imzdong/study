package org.imzdong.study.msb.day_12_gc;

import java.util.LinkedList;
import java.util.List;

public class HelloGc {
    /**
     * 1、java -XX:+PrintCommandLineFlags -version
     * -XX:InitialHeapSize=126399360 -XX:MaxHeapSize=2022389760 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers
     * -XX:+UseCompressedOops -XX:+UseParallelGCC（ps+po）
     * 2、java -Xmn10M -Xms40M -Xmx60M -XX:+PrintCommandLineFlags -XX:+PrintGC  HelloGC
     * 3、-XX:+UseConcMarkSweepGC
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("HelloGC!");
        List list = new LinkedList();
        for(;;) {
            byte[] b = new byte[1024*1024];
            list.add(b);
        }
    }
}
