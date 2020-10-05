package org.imzdong.study.msb.day_10_juc.volatile_;

public class VolatileDemo {

    volatile boolean flag = true;

    public void m(){
        System.out.println("m start");
        while (flag){
            /*try {
                System.out.println("while");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        System.out.println("end");
    }

    public static void main(String[] args) {
        VolatileDemo volatileDemo = new VolatileDemo();
        new Thread(volatileDemo::m).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->volatileDemo.flag=false).start();
    }

}
