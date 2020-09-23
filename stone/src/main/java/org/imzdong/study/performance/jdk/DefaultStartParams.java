package org.imzdong.study.performance.jdk;

/**
 * @description:
 *               1、配置参数监控
 *               -XX:+PrintFLagsFinal 打印所有的系统参数值
 *               -XX:+PrintVMOptions 打印设置的显示参数
 *               -XX:+PrintCommandLineFlags 打印设置的显示和隐式参数
 *               结果：-XX:InitialHeapSize=132483712 -XX:MaxHeapSize=2119739392
 *               -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers
 *               -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation
 *               -XX:+UseParallelGC
 *               2、监控进程参数
 *               jinfo -flags 进程号
 *               jcmd 进程号 VM.flags
 * @author: Winter
 * @time: 2020/1/1
 */
public class DefaultStartParams {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("DefaultStartParams");
        Thread.sleep(60*1000);
    }
}
