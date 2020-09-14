package org.imzdong.study.msb.day_02;

/**
 * 最近不常用算法
 * O（1）
 */
public class LruDemo {

    /**
     * 1、最近常用，不常用的后移（数组达不到O（1），链表）
     * 2、获取常用的达到O（1）（Hash表）
     * 3、移动不常用到头部，不常用前后的变动达到O（1）（双向链表）
     * 使用到链表删除、新增快O（1）、Hash表查询快O(1)
     * @param args
     */
    public static void main(String[] args) {
        LruCache lruCache = new LruCache(3);
        lruCache.put("1","1");
        lruCache.put("2","2");
        System.out.println(lruCache);
        lruCache.put("1","3");
        System.out.println(lruCache);
        lruCache.put("4","4");
        lruCache.put("5","5");
        System.out.println(lruCache);
        lruCache.get("1");
        System.out.println(lruCache);
        /**
         * LruCache{capacity=3,size=2,(2, 2)(1, 1)}
         * LruCache{capacity=3,size=2,(1, 3)(2, 2)}
         * LruCache{capacity=3,size=3,(5, 5)(4, 4)(1, 3)}
         * LruCache{capacity=3,size=3,(1, 3)(5, 5)(4, 4)}
         */
    }
}
