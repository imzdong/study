package org.imzdong.study.performance.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月30日, 0030 17:47
 */
public class ThreadPoolLocal {

    public void testSingleThreadPool(){
        for(int num=0;num<10;num++){
            ExecutorService executorService = Executors.newSingleThreadExecutor(r -> new Thread(r,"SingleThread"));
            executorService.execute(() -> System.out.println("newSingleThreadExecutor"));
        }
    }

    public void testCachedThreadPool(){
        for(int num=0;num<10;num++){
            ExecutorService executorService = Executors.newCachedThreadPool(r -> new Thread(r,"CachedThread:"+ new Random().nextInt(100)));
            executorService.execute(()->System.out.println("newCachedThreadPool"));
        }
    }

    public void testFixedThreadPool(){
        for(int num=0;num<10;num++){
            ExecutorService executorService = Executors.newFixedThreadPool(10,r -> new Thread(r,"FixedThread:"+ new Random().nextInt(100)));
            executorService.execute(()->System.out.println("newFixedThreadPool"));
        }
    }
}
