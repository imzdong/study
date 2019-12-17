package org.imzdong.study.springboot.day03thread.producer_consumer;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/8
 */
public class PcStackMain {

    public static void main(String[] args) {
        PcStack pcStack = new PcStack();
        for(int x=0;x<4;x++) {
            Thread producer = new Thread(new Producer(pcStack), "producer-"+x);
            producer.start();
        }
        for(int num=0;num<4;num++) {
            Thread consumer = new Thread(new Consumer(pcStack), "consumer-"+num);
            consumer.start();
        }
    }

    private static void oneToOne(){
        PcStack pcStack = new PcStack();
        Thread producer = new Thread(new Producer(pcStack), "producer");
        producer.start();
        Thread consumer = new Thread(new Consumer(pcStack), "consumer");
        consumer.start();
    }
}
