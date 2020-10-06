package org.imzdong.study.msb.day_10_juc.lock;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    //可重入，支持公平锁
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        //reentrantLock();
        synLock();
    }

    private static void synLock() {
        Thread thread01 = new Thread(() -> sync(), "Thread01");
        thread01.start();
        Thread thread02 = new Thread(() -> sync(), "Thread02");
        //thread01.interrupt();//在睡觉的时候或者等待的时候可以被interrupt  知识点
        thread02.start();
        System.out.println("thread02被打断");
        thread02.interrupt();//thread02不在获取锁了，结束吧
        System.out.println("main end");
    }

    private static void sync(){
        String name = Thread.currentThread().getName();
        System.out.println(name + "尝试获取锁");
        synchronized (lock) {
            System.out.println(name + " has lock");
            while (true) {
            }
        }
    }


    private static void reentrantLock() {
        Thread thread01 = new Thread(() -> lock(), "Thread01");
        thread01.start();
        Thread thread02 = new Thread(() -> lock(), "Thread02");
        //thread01.interrupt();//在睡觉的时候或者等待的时候可以被interrupt  知识点
        thread02.start();
        try {
            Thread.sleep(1000);
            thread02.interrupt();//thread02不在获取锁了，结束吧
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * Thread01 start try lock
         * Thread01 has lock
         * Thread02 start try lockInterruptibly
         * InterruptedException
         * Thread01 has lock sleep
         */}

    private static void lock(){
        boolean unlock = true;
        String name = Thread.currentThread().getName();
        try {
            if(Objects.equals(name, "Thread02")){
                System.out.println(name +" start try lockInterruptibly");
                lock.lockInterruptibly();//获取锁的时候可以被打断，不是一直等待
            }else {
                System.out.println(name +" start try lock");
                lock.lock();//一直等待获取到锁,不可被中断
            }
            System.out.println(name +" has lock");
            while (true){
            }
        }catch (InterruptedException e){
            //异常会释放锁 知识点
            System.out.println("InterruptedException : "+name+" 不再尝试获取锁");
            unlock = false;
        } finally {
            if(unlock) {
                lock.unlock();
            }
        }
    }
}
