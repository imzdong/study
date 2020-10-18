package org.imzdong.study.msb.day_10_juc.lock.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 写一个固定容器同步器，拥有put和get方法，以及get方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * @author Winter
 */
public class ProductAndConsumerV2 {

    public static void main(String[] args) {
        SynContainerV2 synContainer = new SynContainerV2();
        ExecutorService proPool = Executors.newFixedThreadPool(5);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            proPool.execute(()->{
                while (true) {
                    synContainer.put(random.nextInt(10) + "");
                }
            });
        }

        ExecutorService consumerPool = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 10; i++) {
            consumerPool.execute(()->{
                while (true) {
                    /*System.out.println("count:" + synContainer.getCount());
                    String s = synContainer.get();
                    System.out.println("get:" + s);*/
                    if(synContainer.getCount() > 10){
                        System.out.println("-------------------------------------------------");
                    }
                    if(synContainer.getCount() == 0){
                        System.out.println("---------------------**********----------------------------");
                    }
                }
            });
        }

    }
}

class SynContainerV2{

    List<String> list = new ArrayList<>(10);
    ReentrantLock lock = new ReentrantLock();;
    private Condition conditionW = lock.newCondition();
    private Condition conditionR = lock.newCondition();

    public void put(String str){
        lock.lock();
        //System.out.println(Thread.currentThread().getName()+" acquire ");
        while (list.size() > 9) {
            try {
                conditionW.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(str);
        conditionR.signalAll();
        lock.unlock();
    }

    public String get(){
        lock.lock();
        while (getCount()==0){ //核心while
            try {
                conditionR.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String s = list.get(getCount() - 1);
        conditionW.signalAll();
        list.remove(s);
        lock.unlock();
        return s;
    }

    public int getCount(){
        lock.lock();
        try {
            return list.size();
        }finally {
            lock.unlock();
        }
    }

}
