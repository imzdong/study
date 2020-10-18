package org.imzdong.study.stone.thread;

import java.util.concurrent.*;

/**
 * @description:
 * @author: Winter
 * @time: 2020年8月21日, 0021 16:13
 */
public class ThreadPoolExecutorUtil {

    //核心线程数
    private final static int corePoolSize = 5;
    //最大线程数
    private final static int maximumPoolSize = 10;
    //空闲时间回收线程数至核心线程数
    private final static long keepAliveTime = 5;
    //keepAliveTime的单位
    private final static TimeUnit unit = TimeUnit.MINUTES;
    //等待队列
    private final static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
    //线程工厂
    private final static ThreadFactory threadFactory = r -> {
        //线程池给线程命名
        Thread t = new Thread(r,"如此优秀线程：");
        //设置是否是守护线程
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        //设置线程级别
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    };
    private final static RejectedExecutionHandler rejectedExecutionHandler = (r, e) -> {
        System.out.println("rejectedExecutionHandler");
        System.out.println(e.getQueue());
        System.out.println(r);
    };

    public static ThreadPoolExecutor getPool(){
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                unit, workQueue, threadFactory, rejectedExecutionHandler);

    }
}
