package org.imzdong.study.msb.day_10_juc.collection_;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 线程安全队列
 */
public class QueueDemo {

    private static Queue<Integer> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1_000; i++) {
            queue.add(i);
        }
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (queue.size()>0){
                    System.out.println(queue.poll());
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("end");
    }
}
