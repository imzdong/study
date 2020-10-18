package org.imzdong.study.msb.day_01_operate_sys;

import java.util.concurrent.CountDownLatch;

/**
 * 缓存行合并
 * inter cpu缓存行是64字节 （64*8）位  long：8字节
 */
public class CacheLinePaddingDemo {

    private static volatile long[] not_cacheLine = new long[16]; //64字节

    private static volatile long[] cacheLine = new long[2]; //16字节

    public static void main(String[] args) throws InterruptedException{
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(()->{
            for(long num=0L;num<10_0000_0000L;num++){
                not_cacheLine[0] = num;
            }
            countDownLatch.countDown();
        }).start();
        new Thread(()->{
            for(long num=0L;num<10_0000_0000L;num++){
                not_cacheLine[8] = num;
            }
            countDownLatch.countDown();
        }).start();
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println(String.format("共耗时：%s",end-start));
        System.out.println("-------------------------");
        long startNo = System.currentTimeMillis();
        CountDownLatch countDownLatchNO = new CountDownLatch(2);
        new Thread(()->{
            for(long num=0L;num<10_0000_0000L;num++){
                cacheLine[0] = num;
            }
            countDownLatchNO.countDown();
        }).start();
        new Thread(()->{
            for(long num=0L;num<10_0000_0000L;num++){
                cacheLine[1] = num;
            }
            countDownLatchNO.countDown();
        }).start();
        countDownLatchNO.await();
        long endNo = System.currentTimeMillis();
        System.out.println(String.format("共耗时：%s",endNo-startNo));
    }
}
