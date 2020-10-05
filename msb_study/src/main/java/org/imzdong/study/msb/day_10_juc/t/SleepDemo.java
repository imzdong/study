package org.imzdong.study.msb.day_10_juc.t;

public class SleepDemo {

    private static Object lock = new Object();

    public static void main(String[] args) {
        //sleepTest();
        testYield();
    }

    private static void sleepTest() {
        new Thread(new ThreadDemo()).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){
            System.out.println("main 获取到锁了");
        }
    }

    static void testYield() {
        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("A" + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();

        new Thread(()->{
            for(int i=0; i<100; i++) {
                System.out.println("------------B" + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();
    }

    private static class ThreadDemo implements Runnable{

        @Override
        public void run() {
            synchronized (lock){
                try {
                    System.out.println("start sleep");
                    Thread.sleep(5000);
                    System.out.println("sleep end");
                    /*lock.wait(3000);
                    System.out.println("wait end");*/
                    Thread.yield();
                    for (int i = 0; i < 10; i++) {
                        System.out.println(i);
                    }
                    System.out.println("-----------------------");
                    System.out.println("yield");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
