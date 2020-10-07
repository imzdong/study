package org.imzdong.study.msb.day_10_juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    public static void main(String[] args) {
        Thread thread01 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+":park");
            LockSupport.park();//阻塞
            System.out.println("结束");
        });
        thread01.start();
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(thread01);
        System.out.println("main end");
    }
}
