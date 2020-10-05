package org.imzdong.study.msb.day_10_juc.t;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {

        Object lock = new Object();

        Thread thread = new Thread(new TheadDemo(lock), "线程测试");
        System.out.println("before start "+thread.getState());
        thread.start();
        System.out.println("after start " + thread.getState());

        Thread.sleep(1000);
        System.out.println("sleep :"+thread.getState());
        Thread.sleep(2000);
        System.out.println("wait start:"+thread.getState());
        synchronized (lock) {
            lock.notify();
            System.out.println("notify :" + thread.getState());
        }
        System.out.println("main 释放锁 :" + thread.getState());
        thread.join();//等待线程执行完
        System.out.println("join after:"+thread.getState());
        System.out.println("main end");
    }

    private static class TheadDemo implements Runnable{

        Object lock ;

        public TheadDemo(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println("thread start state :"+ Thread.currentThread().getState());
            try {
                Thread.sleep(2000);
                System.out.println("sleep 结束 "+Thread.currentThread().getState());
                synchronized (lock) {
                    lock.wait();
                    System.out.println("wait 结束 "+Thread.currentThread().getState());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
