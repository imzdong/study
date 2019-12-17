package org.imzdong.study.springboot.day03thread.lock;


/**
 * @description:
 * @author: Winter
 * @time: 2019/12/11
 */
public class LockPcMain {
    public static void main(String[] args) {
        LockPcStack lockPcStack = new LockPcStack();
        for(int x=0;x<5;x++) {
            Thread producer = new Thread(() -> {
                while (true){
                    lockPcStack.push();
                }
            },"producer"+x);
            producer.start();
        }
        for(int x=0;x<5;x++) {
            Thread consumer = new Thread(() -> {
                while (true){
                    lockPcStack.pop();
                }
            },"consumer"+x);
            consumer.start();
        }
    }
}
