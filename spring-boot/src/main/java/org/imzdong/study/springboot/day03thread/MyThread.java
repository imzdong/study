package org.imzdong.study.springboot.day03thread;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/3
 */
public class MyThread extends Thread {

    public MyThread(){
        System.out.println("Thread.currentThread().getName():"+Thread.currentThread().getName());
        System.out.println("this.getName():"+this.getName());
        System.out.println("this.currentThread().getName():"+this.currentThread().getName());
        System.out.println("getName():"+getName());
        System.out.println("currentThread().getName():"+currentThread().getName());
    }

    @Override
    public void run() {
        System.out.println("run:Thread.currentThread().getName():"+Thread.currentThread().getName());
        System.out.println("run:this.getName():"+this.getName());
        System.out.println("run:this.currentThread().getName():"+this.currentThread().getName());
        System.out.println("run:getName():"+getName());
        System.out.println("run:currentThread().getName():"+currentThread().getName());
        System.out.println("this.isAlive():"+this.isAlive());
        System.out.println("end");
    }

    /**
     * Thread.currentThread().getName():main
     * this.getName():Thread-0
     * this.currentThread().getName():main
     * getName():Thread-0
     * currentThread().getName():main
     * run:Thread.currentThread().getName():Winter
     * run:this.getName():Thread-0
     * run:this.currentThread().getName():Winter
     * run:getName():Thread-0
     * run:currentThread().getName():Winter
     * @param args
     */

    public static void main(String[] args) {
        /*Thread thread = new Thread(new MyThread());
        thread.setName("Winter");
        thread.start();*/

        MyThread myThread = new MyThread();
        myThread.start();
    }
    /**
     * Thread.currentThread().getName():main
     * this.getName():Thread-0
     * this.currentThread().getName():main
     * getName():Thread-0
     * currentThread().getName():main
     * run:Thread.currentThread().getName():Thread-0
     * run:this.getName():Thread-0
     * run:this.currentThread().getName():Thread-0
     * run:getName():Thread-0
     * run:currentThread().getName():Thread-0
     * this.isAlive():true
     * end
     */
}
