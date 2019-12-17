package org.imzdong.study.springboot.day03thread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/11
 */
public class LockPcStack {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private List<String> list = new ArrayList<>(1);
    private Condition condition = reentrantLock.newCondition();

    public void push(){
        reentrantLock.lock();
        while (list.size()>=1){
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add("Winter"+new Random().nextInt(1000));
        System.out.println("push");
        condition.signalAll();
    }

    public void pop(){
        reentrantLock.lock();
        while (list.size()<1){
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(list.get(0));
        list.remove(0);
        System.out.println("pop");
        condition.signalAll();
    }
}
