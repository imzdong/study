package org.imzdong.study.msb.day_10_juc.refer;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用：gc的时候就回收
 * @see ThreadLocal
 * @author Winter
 */
public class WeakReferDemo {

    public static void main(String[] args) {
        WeakReference<Byte[]> weakReference = new WeakReference<>(new Byte[1024 * 1024 * 10]);
        System.out.println(weakReference.get());
        MemoryUtil.print();
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MemoryUtil.print();
        System.out.println(weakReference.get());
    }
}
