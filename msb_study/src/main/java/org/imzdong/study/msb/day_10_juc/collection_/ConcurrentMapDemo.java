package org.imzdong.study.msb.day_10_juc.collection_;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapDemo {
    static int size = 1_000_000;
    static Integer[] keys = new Integer[size];
    static Integer[] values = new Integer[size];

    public static void main(String[] args) throws Exception{
        for (int i = 0; i < size; i++) {
            keys[i] = i;
            values[i] = i;
        }
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
        //new Hashtable();
                //new ConcurrentHashMap<>();
                //new HashMap<>(size);
                //new Hashtable<>(size); 287
        int threadSize = 10_000;
        int threadCount = size/threadSize;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = getThread(i, threadSize, map);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }
        long end = System.currentTimeMillis();
        System.out.println(map.size());
        System.out.println(end - start);

    }

    private static Thread getThread(final int page, final int threadSize, Map<Integer, Integer> hashtable){
        return new Thread(()->{
            int start = page*threadSize;
            for (int num=start;num<start+threadSize;num++){
                hashtable.put(keys[num], values[num]);
            }
        });
    }
}
