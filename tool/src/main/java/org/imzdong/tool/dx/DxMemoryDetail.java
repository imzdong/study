package org.imzdong.tool.dx;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.TimeUnit;

/**
 * @author dong.zhou
 * @since 2022/7/28 10:07
 * https://blog.csdn.net/uuqaz/article/details/123340729
 */
public class DxMemoryDetail {

    public static void main(String[] args) throws Exception{
        //jvmDetail();
        //dxMemory();
        //pxLock();
        //qljLock();
        zljLock();
    }

    private static void jvmDetail(){
        System.out.println(VM.current().details());
    }

    /**
     * 锁对象刚创建时，没有任何线程竞争，对象处于无锁状态。在上面打印的空对象的内存布局中，根据大小端，
     * 得到最后8位是00000001，表示处于无锁态，并且处于不可偏向状态。这是因为在jdk中偏向锁存在延迟4秒启动，
     * 也就是说在jvm启动后4秒后创建的对象才会开启偏向锁，我们通过jvm参数取消这个延迟时间：
     * -XX:BiasedLockingStartupDelay=0
     */
    private static void dxMemory(){
        Dx dx = new Dx();
        ClassLayout classLayout = ClassLayout.parseInstance(dx);
        String s = classLayout.toPrintable();
        System.out.println(s);
        /**

         org.imzdong.tool.dx.Dx object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         8     4        (object header)                           43 c0 00 f8 (01000011 11000000 00000000 11111000) (-134168509)
         12     4        (loss due to the next object alignment)
         Instance size: 16 bytes
         Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */
    }

    /**
     * 在没有线程竞争的条件下，第一个获取锁的线程通过CAS将自己的threadId写入到该对象的mark word中，
     * 若后续该线程再次获取锁，需要比较当前线程threadId和对象mark word中的threadId是否一致，如果一致那么可以直接获取，
     * 并且锁对象始终保持对该线程的偏向，也就是说偏向锁不会主动释放。
     */
    private static void pxLock(){
        Dx dx = new Dx();
        synchronized (dx){
            System.out.println(ClassLayout.parseInstance(dx).toPrintable());
        }

        System.out.println(ClassLayout.parseInstance(dx).toPrintable());

        synchronized (dx){
            System.out.println(ClassLayout.parseInstance(dx).toPrintable());
        }
    }

    /**
     * 当两个或以上线程交替获取锁，但并没有在对象上并发的获取锁时，偏向锁升级为轻量级锁。
     * 在此阶段，线程采取CAS的自旋方式尝试获取锁，避免阻塞线程造成的cpu在用户态和内核态间转换的消耗。
     *
     * 主线程首先对dx对象加锁，首次加锁为101偏向锁
     * 子线程等待主线程释放锁后，对dx对象加锁，这时将偏向锁升级为00轻量级锁
     * 轻量级锁解锁后，dx对象无线程竞争，恢复为001无锁态，并且处于不可偏向状态。
     * 如果之后有线程再尝试获取dx对象的锁，会直接加轻量级锁，而不是偏向锁
     */
    private static void qljLock() throws Exception{
        Dx dx = new Dx();
        synchronized (dx){
            System.out.println(ClassLayout.parseInstance(dx).toPrintable());
        }

        Thread thread = new Thread(() -> {
            synchronized (dx) {
                System.out.println("--THREAD--:"+ClassLayout.parseInstance(dx).toPrintable());
            }
        });
        thread.start();
        thread.join();
        System.out.println("--END--:"+ClassLayout.parseInstance(dx).toPrintable());
    }

    private static void zljLock(){
        Dx dx = new Dx();
        new Thread(() -> {
            synchronized (dx) {
                System.out.println("--THREAD1--:" + ClassLayout.parseInstance(dx).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (dx) {
                System.out.println("--THREAD2--:" + ClassLayout.parseInstance(dx).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
