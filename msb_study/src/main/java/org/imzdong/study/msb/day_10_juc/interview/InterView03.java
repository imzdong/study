package org.imzdong.study.msb.day_10_juc.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 1A2B3C。。。。
 */
public class InterView03 {

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {

        List<String> zm = new ArrayList<>(26);
        for (char c = 'a'; c <= 'z'; c++) {
            zm.add(c+"");
        }
        CountDownLatch countDownLatch = new CountDownLatch(2);
        List<String> sz = new ArrayList<>(26);
        for (int c = 1; c <= 26; c++) {
            sz.add(c+"");
        }
        t1 = new Thread(() -> {
            for (String s : zm) {
                System.out.print(s);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
            LockSupport.unpark(t2);
            countDownLatch.countDown();
        });

        t2 = new Thread(() -> {
            for (String s : sz) {
                LockSupport.park();
                System.out.print(s);
                LockSupport.unpark(t1);

            }
            LockSupport.unpark(t1);
            countDownLatch.countDown();
        });
        t1.start();
        t2.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("end");
    }
}
