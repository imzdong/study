package org.imzdong.study.msb.day_10_juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        LockDemo lockDemo = new LockDemo(readWriteLock);
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] =  new Thread(()->{
                lockDemo.write(lockDemo.read());
            });
        }
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            thread.start();
        }
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(lockDemo.count);
    }
    static class LockDemo{

        ReadWriteLock readWriteLock;
        private int count;

        public LockDemo(ReadWriteLock readWriteLock){
            this.readWriteLock = readWriteLock;
        }

        public synchronized int read(){
            //readWriteLock.readLock().lock();
            String name = Thread.currentThread().getName();
            //System.out.println(name + " read " + count);
            //readWriteLock.readLock().unlock();
            return count;
        }

        public synchronized void write(int countR){
            //readWriteLock.writeLock().lock();
            String name = Thread.currentThread().getName();
            count = countR;
            count++;
            //System.out.println(name + " write ");
            //readWriteLock.writeLock().unlock();
        }
    }
}
