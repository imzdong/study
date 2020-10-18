package org.imzdong.study.msb.day_10_juc.refer;

import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {

    private static ThreadLocal tl = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException{
        new Thread(()->{
            String name = Thread.currentThread().getName();
            tl.set(name+":Winter");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+":"+tl.get());
        }).start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{
            String name = Thread.currentThread().getName();
            System.out.println(name+":"+tl.get());
        }).start();
    }
}
