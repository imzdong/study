package org.imzdong.study.stone.base;

/**
 * 刚刚第一个使用了 volatile 关键字的例子里，因为所有数据的读和写都来自主内存。
 * 那么自然地，我们的 ChangeMaker 和 ChangeListener 之间，看到的 COUNTER 值就是一样的。
 *
 * 到了第二段进行小小修改的时候，我们去掉了 volatile 关键字。这个时候，ChangeListener 又是一个忙等待的循环，
 * 它尝试不停地获取 COUNTER 的值，这样就会从当前线程的“Cache”里面获取。于是，这个线程就没有时间从主内存里面同步更新后的 COUNTER 值。
 * 这样，它就一直卡死在 COUNTER=0 的死循环上了。
 *
 * 而到了我们再次修改的第三段代码里面，虽然还是没有使用 volatile 关键字，但是短短 5ms 的 Thead.Sleep 给了这个线程喘息之机。
 * 既然这个线程没有这么忙了，它也就有机会把最新的数据从主内存同步到自己的高速缓存里面了。
 * 于是，ChangeListener 在下一次查看 COUNTER 值的时候，就能看到 ChangeMaker 造成的变化了。
 *
 * 虽然 Java 内存模型是一个隔离了硬件实现的虚拟机内的抽象模型，但是它给了我们一个很好的“缓存同步”问题的示例。
 * 也就是说，如果我们的数据，在不同的线程或者 CPU 核里面去更新，因为不同的线程或 CPU 核有着自己各自的缓存，很有可能在 A 线程的更新，到 B 线程里面是看不见的。
 * @author admin
 * @date 2021/8/8 下午3:03
 */
public class VolatileTest {

    private static volatile int COUNTER = 0;

    public static void main(String[] args) {

        new Thread(() -> {
            int threadValue = COUNTER;
            while (threadValue < 5) {
                if (threadValue != COUNTER) {
                    System.out.println("Got Change for COUNTER : " + COUNTER + "");
                    threadValue = COUNTER;
                }
            }
        }, "compare").start();

        new Thread(() -> {
            int threadValue = COUNTER;
            while (COUNTER < 5) {
                System.out.println("Incrementing COUNTER to : " + (threadValue + 1) + "");
                COUNTER = ++threadValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "sub").start();

    }
}
