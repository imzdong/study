package org.imzdong.study.msb.io.netty;

public class ThreadTest {

    public static void main(String[] args) {
        System.out.println("main start");
        new Thread(()->{
            try {
                System.out.println("thread start");
                Thread.sleep(10000);
                System.out.println("thread end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("mani end");
    }
}
