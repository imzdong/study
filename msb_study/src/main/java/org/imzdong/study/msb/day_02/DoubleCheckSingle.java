package org.imzdong.study.msb.day_02;

/**
 * @description:
 * @author: Winter
 * @time: 2020年9月16日, 0016 11:07
 */
public class DoubleCheckSingle {

    private volatile Single single;
    private byte[] lock = new byte[10];

    private Single getSingle(){
        String name = Thread.currentThread().getName();
        System.out.println(name);
        if(single == null){
            synchronized (lock){
                System.out.println(name+"get lock");
                if(single == null){
                    SingleB singleB = new SingleB();
                    singleB.setB("b");
                    single = new Single(singleB,"A");
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            System.out.println(name + "return");
        }
        return single;
    }

    public static void main(String[] args) {
        DoubleCheckSingle doubleCheckSingle = new DoubleCheckSingle();
        new Thread(()->{
            Single single = doubleCheckSingle.getSingle();
            System.out.println("A:"+single);
        },"A").start();
        new Thread(()->{
            Single single = doubleCheckSingle.getSingle();
            System.out.println("B:"+single);
        },"B").start();
        
    }
}
