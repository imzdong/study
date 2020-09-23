package org.imzdong.study.performance.redis;

import org.imzdong.study.performance.thread.ThreadPoolExecutorUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:
 * @author: Winter
 * @time: 2020年8月23日, 0023 11:02
 */
public class OneHash {

    public static void main(String[] args) throws Exception{
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String name = runtimeMXBean.getName();
        System.out.println("name:"+name);
        ThreadPoolExecutor poolExecutor = ThreadPoolExecutorUtil.getPool();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        threadInfo(bean);
        int listNum = 6;
        for(int num=1;num<=listNum;num++) {
            realExe(listNum,num-1,poolExecutor,1000);
        }
        threadInfo(bean);
        Thread.sleep(60000);
        poolExecutor.shutdown();
        threadInfo(bean);
    }

    private static void threadInfo(ThreadMXBean bean){
        long[] allThreadIds = bean.getAllThreadIds();
        for(long l:allThreadIds){
            System.out.println(l);
        }
        System.out.println("getThreadCount:"+bean.getThreadCount());
    }

    private static void realExe(final int num,final int count, ThreadPoolExecutor poolExecutor, final int total){
        poolExecutor.execute(() -> {
            List<Integer> list = new ArrayList<>();
            for (int page = 1; page <= total; page++) {
                if(page%num==count){
                    list.add(page);
                }
            }
            System.out.println(list);
        });
    }

    private static void realExeBack(final int num, ThreadPoolExecutor poolExecutor, final int total){
        poolExecutor.execute(() -> {
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int page = 1; page <= total; page++) {
                int c = page%num;
                if(map.containsKey(c)){
                    map.get(c).add(page);
                }else {
                    List<Integer> l = new ArrayList<>();
                    l.add(c);
                    map.put(c,l);
                }

            }
            Set<Integer> keys = map.keySet();
            for(Integer key:keys) {
                System.out.println(key+":"+map.get(key).size());
            }
            poolExecutor.shutdown();
        });
    }
}
