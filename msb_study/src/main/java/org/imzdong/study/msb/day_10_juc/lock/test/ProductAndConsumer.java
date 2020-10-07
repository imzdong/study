package org.imzdong.study.msb.day_10_juc.lock.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 写一个固定容器同步器，拥有put和get方法，以及get方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 * @author Winter
 */
public class ProductAndConsumer {

    public static void main(String[] args) {
        SynContainer synContainer = new SynContainer();
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
                    System.out.println("count:" + synContainer.getCount());
                    String s = synContainer.get();
                    System.out.println("get:" + s);
                    if (synContainer.getCount() > 10) {
                        System.out.println("-------------------------------------------------");
                    }
                }
            });
        }

    }
}
class SynContainer{

    List<String> list = new ArrayList<>(10);

    private Object lock = new Object();

    public void put(String str){
        try {
            synchronized (lock) {
                while (getCount() > 9) {
                    lock.wait();
                }
                list.add(str);
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
        }

    }

    public String get(){
        synchronized (lock) {
            while (getCount()==0){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String s = list.get(getCount() - 1);
            lock.notifyAll();
            list.remove(s);
            return s;
        }
    }

    public int getCount(){
        synchronized (lock){
            return list.size();
        }
    }

}
