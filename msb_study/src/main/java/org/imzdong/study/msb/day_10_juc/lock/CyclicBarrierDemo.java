package org.imzdong.study.msb.day_10_juc.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 栅栏：凑齐n个线程在执行需要执行的线程
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println(" CyclicBarrier start "));
        new Thread(()-> {
            try {
                System.out.println(Thread.currentThread().getName() + " start");
                cyclicBarrier.await();//阻塞线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "thread01").start();
        new Thread(()-> {
            try {
                System.out.println(Thread.currentThread().getName() + " start");
                cyclicBarrier.await();//阻塞线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "thread02").start();
        new Thread(()-> {
            try {
                System.out.println(Thread.currentThread().getName() + " start");
                cyclicBarrier.await();//阻塞线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "thread03").start();
        new Thread(()-> {
            try {
                System.out.println(Thread.currentThread().getName() + " start");
                cyclicBarrier.await();//阻塞线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "thread04").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" main end ");
    }
}
