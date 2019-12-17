package org.imzdong.study.springboot.day03thread.producer_consumer;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/8
 */
public class Producer implements Runnable {

    private PcStack pcStack;

    public Producer(PcStack pcStack){
        this.pcStack = pcStack;
    }

    @Override
    public void run() {
        while (true) {
            pcStack.push();
        }
    }
}
