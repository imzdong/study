package org.imzdong.study.msb.day_10_juc.refer;

import java.util.concurrent.TimeUnit;

/**
 * 强引用：只有置为null，gc时回收
 * @author Winter
 */
public class StrongReferDemo {

    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        ReferDemo o = new ReferDemo();
        System.out.println(name+":"+o);
        MemoryUtil.print();
        o = null;
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MemoryUtil.print();
        System.out.println(name+":"+o);
        //再次GC看属性是否被回收
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MemoryUtil.print();
    }
}
