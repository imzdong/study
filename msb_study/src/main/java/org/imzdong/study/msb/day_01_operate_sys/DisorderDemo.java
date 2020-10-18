package org.imzdong.study.msb.day_01_operate_sys;

import java.util.concurrent.CountDownLatch;

/**
 * 验证cpu乱序
 * a=1;x=b;b=1;y=a x=0;y=1
 * a=1;b=1;x=b;y=a x=1;y=1
 * a=1;b=1;y=a;x=b x=1;y=1
 * 各种组合，不可能x=0；y=0
 */
public class DisorderDemo {

    private static int x=0, y=0, a=0, b=0;
    private static String str = "str";

    public static void main(String[] args) throws InterruptedException{
        int i=0;
        //setStr("tt",3);
        for(;;) {
            i++;
            CountDownLatch downLatch = new CountDownLatch(2);
            new Thread(() -> {
                a=1;
                x=b;
                downLatch.countDown();
            }).start();
            new Thread(() -> {
                b=1;
                y=a;
                downLatch.countDown();
            }).start();
            downLatch.await();
            System.out.println(String.format("第%s次;x:%s;y:%s",i,x,y));
            if(x==0&&y==0){
                System.out.println(String.format("第%s次;x:%s;y:%s",i,x,y));
                break;
            }

        }
    }

    private static void setStr(String str,int x){
        System.out.println(str);
        System.out.println(x);
    }
}
