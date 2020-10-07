package org.imzdong.study.msb.day_10_juc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch：门栓锁
 * 控制线程顺序
 */
public class CountDownDemo {

    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {
        new Thread(()->say(),"thread01").start();
        new Thread(()->say(),"thread02").start();
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }

    private static void say(){
        String name = Thread.currentThread().getName();
        System.out.println(name+" start ");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "执行完了");
        countDownLatch.countDown();
    }
}
