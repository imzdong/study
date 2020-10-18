package org.imzdong.study.msb.day_10_juc.lock.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 分析下面这个程序，能完成这个功能吗？
 * @author Winter
 */
public class TbTestCountDown {

    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {

        CountDownLatch t1 = new CountDownLatch(1);
        CountDownLatch t2 = new CountDownLatch(1);

        new Thread(()->{
            while (true) {
                try {
                    t2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 结束。");
                t1.countDown();
                break;
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                list.add(i + "");
                System.out.println(i);
                if(list.size() == 5){
                    t2.countDown();
                    try {
                        t1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        System.out.println("main end");
    }
}
