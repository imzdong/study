package org.imzdong.study.msb.day_10_juc.t;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateThread {

    public static void main(String[] args) {
        //1、thread
        new ThreadDemo("Thread形式").start();
        //调用run方法就是方法调用。
        //2、runnable形式
        new Thread(new RunnableDemo(), "Runnable形式").start();
        //3、线程池调用
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(()->{
            System.out.println(Thread.currentThread().getName()+" start");
        });
        pool.shutdown();
        System.out.println("main end");
    }
}

class ThreadDemo extends Thread{

    public ThreadDemo(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" start");
    }
}
class RunnableDemo implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" start");
    }
}
