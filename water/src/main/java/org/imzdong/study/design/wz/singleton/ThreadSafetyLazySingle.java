package org.imzdong.study.design.wz.singleton;

/**
 * @desc 线程安全的懒汉单例模式
 * @author Winter
 * @date 2020-05-10 12:09
 */
public class ThreadSafetyLazySingle{

    private static ThreadSafetyLazySingle INSTANCE ;
    /**
     * 1、线程构造私有
     */
    private ThreadSafetyLazySingle(){
    }
    /**
     * 2、获取实例
     * @return
     */
    public  static synchronized ThreadSafetyLazySingle getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ThreadSafetyLazySingle();
        }
        return INSTANCE;
    }

}