package org.imzdong.study.performance.thread.lock;

import java.util.concurrent.*;

/**
 * @description:
 * @author: Winter
 * @time: 2020年6月16日, 0016 12:46
 */
public class UnLockDemo {

    private int num = 0;

    private int synGet(){
        return num;
    }

    private void synSet(){
        num++ ;
    }

    public static void main(String[] args) throws Exception{
        UnLockDemo unLockDemo = new UnLockDemo();
        //ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,8,1000L,);
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newCachedThreadPool(r->new Thread(r, "winter_pool_" + r.hashCode()));
        service.execute(new DemoTask(unLockDemo, latch));
        service.execute(new DemoTask(unLockDemo, latch));
        service.execute(new DemoTask(unLockDemo, latch));
        service.execute(new DemoTask(unLockDemo, latch));
        service.execute(new DemoTask(unLockDemo, latch));
        latch.await();
        System.out.println(unLockDemo.synGet());
        service.shutdown();
    }

    private static class DemoTask implements Runnable{

        private UnLockDemo demo;
        private CountDownLatch latch;

        DemoTask(UnLockDemo demo, CountDownLatch latch){
            this.demo = demo;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+": start");
            for(int num = 0;num<50000;num++) {
                demo.synSet();
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName()+": end");
        }
    }
}
