package org.imzdong.study.springboot.day03thread;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/3
 */
public class MyThreadInterrupted extends Thread {

    @Override
    public void run() {
        for(int num=0;num<500000;num++){
            if(this.isInterrupted()){
                System.out.println("停止");
                break;
            }
            System.out.println("num:"+(num++));
        }
    }

    public static void main(String[] args) {
        MyThreadInterrupted myThreadInterrupted = new MyThreadInterrupted();
        myThreadInterrupted.start();
        try {
            Thread.sleep(2000L);
            myThreadInterrupted.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
