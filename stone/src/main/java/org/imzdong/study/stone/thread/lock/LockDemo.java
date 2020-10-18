package org.imzdong.study.stone.thread.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: Winter
 * @time: 2020年6月16日, 0016 12:46
 */
public class LockDemo {

    private int num = 0;

    private ReentrantLock rl = new ReentrantLock();

    private int synGet(){
        return num;
    }

    private void synSet(){
        rl.lock();
        try {
            num++;
        }finally {
            rl.unlock();
        }
    }

    public static void main(String[] args) throws Exception{
        LockDemo unLockDemo = new LockDemo();
        //ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,8,1000L,);
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newCachedThreadPool(r->new Thread(r, "winter_pool_" + r.hashCode()));
        service.execute(new DemoTask(unLockDemo, latch,"1"));
        service.execute(new DemoTask(unLockDemo, latch,"2"));
        service.execute(new DemoTask(unLockDemo, latch,"3"));
        service.execute(new DemoTask(unLockDemo, latch,"4"));
        service.execute(new DemoTask(unLockDemo, latch,"5"));
        latch.await();
        System.out.println(unLockDemo.synGet());
        service.shutdown();
    }

    private static class DemoTask implements Runnable{

        private LockDemo demo;
        private CountDownLatch latch;
        private String name;

        DemoTask(LockDemo demo, CountDownLatch latch, String name){
            this.demo = demo;
            this.latch = latch;
            this.name = name;
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

        @Override
        public String toString() {
            return "DemoTask{" +
                    "demo=" + demo +
                    ", latch=" + latch +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
