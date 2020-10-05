package org.imzdong.study.msb.day_10_juc.syn;

/**
 * 可重入
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        SynchronizedT synchronizedT = new SynchronizedT();
        new Thread(()->synchronizedT.lock()).start();
        synchronizedT.unLock();
        synchronizedT.lock2();
    }

}
class SynchronizedT{

    /**
     * 锁的是对象本身
     */
    public synchronized void lock(){
        try {
            lock2();
            System.out.println("lock");
            Thread.sleep(2000);
            System.out.println("lock after");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void lock2(){
        System.out.println(Thread.currentThread().getName()+" lock2");
    }

    public void unLock(){
        System.out.println("unlock");
    }
}
