package org.imzdong.study.springboot.day03thread.producer_consumer;

import java.util.Random;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/8
 */
public class PcStack {

    private String [] stacks = new String[1];

    synchronized public void push(){
        String name = Thread.currentThread().getName();
        while (stacks[0]!=null){
            try {
                System.out.println("CurrentThreadName:"+name+"-push Waiting");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stacks[0] = "Winter"+ new Random().nextInt(100);
        System.out.println("CurrentThreadName:"+name+"-push :"+stacks[0]);
        this.notifyAll();
    }

    synchronized public void pop(){
        String name = Thread.currentThread().getName();
        while (stacks[0]==null){
            try {
                System.out.println("CurrentThreadName:"+name+"-pop Waiting");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("CurrentThreadName:"+name+"-pop :"+stacks[0].toString());
        stacks[0] = null;
        this.notifyAll();
    }

}
