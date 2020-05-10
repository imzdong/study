package org.imzdong.study.design.singleton;

/**
 * @desc 线程安全的饿汉单例模式
 * @author Winter
 * @date 
 */
public class ThreadSafetyHungrySingle{

    private final static ThreadSafetyHungrySingle INSTANCE = new ThreadSafetyHungrySingle();
    /**
     * 1、线程构造私有
     */
    private ThreadSafetyHungrySingle(){
    }
    /**
     * 2、获取实例
     * @return
     */
    public static ThreadSafetyHungrySingle getInstance(){
        return INSTANCE;
    }

}