package org.imzdong.study.msb.day_10_juc.refer;

import java.lang.ref.SoftReference;

/**
 * 软引用：内存不够用时回收。-Xmx15M -Xms15M
 * 缓存时使用
 * @author Winter
 */
public class SoftReferDemo {

    public static void main(String[] args) {
        mySoft();
        //byteSoft();
    }
    //-Xmx200M -Xms200M
    private static void byteSoft(){
        //100M的缓存数据
        //将缓存数据用软引用持有
        SoftReference<byte[]> cacheRef = new SoftReference<>(new byte[100 * 1024 * 1024]);
        //将缓存数据的强引用去除
        System.out.println("第一次GC前" + cacheRef.get());
        //进行一次GC后查看对象的回收情况
        System.gc();
        //等待GC
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("第一次GC后" + cacheRef.get());

        //在分配一个120M的对象，看看缓存对象的回收情况
        byte[] newData = new byte[120 * 1024 * 1024];
        System.out.println("分配后" + cacheRef.get());
    }
    //TODO 为啥会内存溢出 对象被回收 对象的属性是否也被回收了
    // 对象被回收，它的属性对象为被回收，但是已经不可达，只有再次经过gc就回收了
    private static void mySoft() {
        MemoryUtil.print();
        String name = Thread.currentThread().getName();
        SoftReference<ReferDemo> o = new SoftReference<>(new ReferDemo());
        //o是强引用，ReferDemo（10M）是软引用
        System.out.println(name+":"+o.get());
        System.out.println(name+":"+o);
        MemoryUtil.print();
        //再次创建内存不足Finalizer:ReferDemo finalize 回收软引用
        byte[] newData = new byte[60 * 1024 * 1024];
        MemoryUtil.print();
        System.out.println(name+":"+o.get());
        System.out.println(name+":"+o);
    }
}
