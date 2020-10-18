package org.imzdong.study.msb.day_10_juc.refer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemoryUtil {

    public static void print(){
        System.out.println("=======内存start======");
        MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = bean.getHeapMemoryUsage();
        System.out.println(memoryUsage.getUsed());
        System.out.println("=======内存end======");
    }
}
