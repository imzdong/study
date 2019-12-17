package org.imzdong.study.springboot.day03thread.producer_consumer;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/8
 */
public class Consumer implements Runnable {

    private PcStack pcStack;

    public Consumer(PcStack pcStack){
        this.pcStack = pcStack;
    }

    @Override
    public void run() {
        while (true) {
            pcStack.pop();
        }
    }
}
