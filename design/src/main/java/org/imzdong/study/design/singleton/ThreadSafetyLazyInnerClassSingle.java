package org.imzdong.study.design.singleton;

/**
 * @desc 线程安全的懒汉单例模式-内部类实现
 * @author Winter
 * @date 2020-05-10 12:09
 */
public class ThreadSafetyLazyInnerClassSingle{

    /**
     * 1、线程构造私有
     */
    private ThreadSafetyLazyInnerClassSingle(){
    }

    private static class ThreadSafetyLazyInnerClassSingleHandler{
        static ThreadSafetyLazyInnerClassSingle INSTANCE =  new ThreadSafetyLazyInnerClassSingle();
    }
    /**
     * 2、获取实例
     * @return
     */
    public  static synchronized ThreadSafetyLazyInnerClassSingle getInstance(){
        return ThreadSafetyLazyInnerClassSingleHandler.INSTANCE;
    }

}