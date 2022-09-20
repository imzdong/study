package org.imzdong.tool.performance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author dong.zhou
 * @since 2022/7/13 11:33
 */
public class ParallelTest {
    static void xnTest() {
        //paraTest();
        completeTest();
    }

    private static void paraTest() {
        List<Integer> numbers = Arrays.asList(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26, 27, 28, 29
        );
        final long begin = System.currentTimeMillis();
        List<Integer> collect = numbers.parallelStream().map(k -> {
            try {
                Thread.sleep(1000);
                //System.out.println((System.currentTimeMillis() - begin) + "ms => " + k + " \t" + Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return k;
        }).collect(Collectors.toList());

        System.out.println(Thread.currentThread() + "total:" + (System.currentTimeMillis() - begin) +"--list:"+collect);
    }

    public static void main(String[] args) {
        //    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
        final long begin = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(8);
        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                xnTest();
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - begin);
        /**
         * Thread[Thread-0,5,main]total:8020--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-4,5,main]total:11021--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-2,5,main]total:13024--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-3,5,main]total:15024--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-7,5,main]total:15023--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-5,5,main]total:15024--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-1,5,main]total:15025--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-6,5,main]total:16025--list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * 16032
         */

        /**
         * Thread[Thread-7,5,main]total:2003---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-0,5,main]total:2009---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-4,5,main]total:2004---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-5,5,main]total:2004---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-2,5,main]total:2006---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-6,5,main]total:2006---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-1,5,main]total:2010---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * Thread[Thread-3,5,main]total:2004---list:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
         * 2074
         */
        executor.shutdown();
    }

    private final static ExecutorService executor = Executors.newFixedThreadPool(200);


    static void completeTest() {
        List<Integer> numbers = Arrays.asList(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26, 27, 28, 29
        );
        final long begin = System.currentTimeMillis();
        ArrayList<CompletableFuture<String>> list = new ArrayList<>();
        for (Integer number : numbers) {
            CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(()->{
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return number.toString();
            }, executor);
            list.add(orgFuture);
        }
        List<String> collect = list.stream().map(m -> {
            try {
                return m.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        System.out.println(Thread.currentThread() + "total:" + (System.currentTimeMillis() - begin) + "---list:" + collect );
    }
}
