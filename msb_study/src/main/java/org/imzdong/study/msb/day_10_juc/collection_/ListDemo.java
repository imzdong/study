package org.imzdong.study.msb.day_10_juc.collection_;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ListDemo {

    private static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1_000; i++) {
            list.add(i);
        }
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (list.size()>0){
                    /*try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    System.out.println(list.remove(0));
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("end");
    }
}
