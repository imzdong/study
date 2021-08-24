package org.imzdong.study.stone.base;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author admin
 * @since 2021/8/24 上午10:23
 */
public class MultiThread {

    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        for (int i = 0; i < threadInfos.length; i++) {
            ThreadInfo threadInfo = threadInfos[i];
            System.out.println(threadInfo);
            System.out.println(threadInfo.getThreadId()+":"+threadInfo.getThreadName());
        }
    }
}
