package org.imzdong.study.msb.design.single;

/**
 * 懒加载，double check
 */
public class SingleLazy {

    private SingleLazy(){}
    //防止重排序导致其他线程获取未完全初始化对象
    private static volatile SingleLazy singleLazy;
    private final static byte[] lock = new byte[10];

    public static SingleLazy getInstance(){
        if(singleLazy == null){
            // 实验是否会实例多个start
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 实验是否会实例多个end
            synchronized (lock){
                if(singleLazy == null){
                    singleLazy = new SingleLazy();
                }
            }
        }
        return singleLazy;
    }

    public void lazy(){
        System.out.println("I'm lazy!!!");
    }
}
