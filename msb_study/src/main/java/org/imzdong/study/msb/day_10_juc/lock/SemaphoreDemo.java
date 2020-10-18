package org.imzdong.study.msb.day_10_juc.lock;

import java.util.concurrent.Semaphore;

/**
 * 限流
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+" 结束 ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();
        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName()+" 结束 ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();
        System.out.println("main end");
    }
}
