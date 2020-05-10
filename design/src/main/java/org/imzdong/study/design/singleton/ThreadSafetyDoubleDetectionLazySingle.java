package org.imzdong.study.design.singleton;

/**
 * @desc 线程安全的双重检测懒汉单例模式
 * @author Winter
 * @date 2020-05-10 12:09
 */
public class ThreadSafetyDoubleDetectionLazySingle{

    private static ThreadSafetyDoubleDetectionLazySingle INSTANCE ;
    /**
     * 1、线程构造私有
     */
    private ThreadSafetyDoubleDetectionLazySingle(){
    }
    /**
     * 2、获取实例
     * @return
     */
    public  static ThreadSafetyDoubleDetectionLazySingle getInstance(){
        if(INSTANCE == null){
            synchronized(ThreadSafetyDoubleDetectionLazySingle.class){
                if(INSTANCE == null){
                    INSTANCE = new ThreadSafetyDoubleDetectionLazySingle();
                }               
            }            
        }
        return INSTANCE;
    }

}