package org.imzdong.study.msb.day_10_juc.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 1A2B3C。。。。
 */
public class InterView02 {

    public static void main(String[] args) {

        Object lock = new Object();

        List<String> zm = new ArrayList<>(26);
        for (char c = 'a'; c <= 'z'; c++) {
            zm.add(c+"");
        }
        List<String> sz = new ArrayList<>(26);
        for (int c = 1; c <= 26; c++) {
            sz.add(c+"");
        }
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(()->{
            synchronized (lock){
                for (String s: zm) {
                    System.out.print(s);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
                countDownLatch.countDown();
            }
        }).start();

        new Thread(()->{
            synchronized (lock){
                for (int i = 0; i < sz.size(); i++) {
                    System.out.print(sz.get(i));
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
                countDownLatch.countDown();
            }
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("end");
    }
}
