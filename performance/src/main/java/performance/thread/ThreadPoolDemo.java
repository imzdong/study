package performance.thread;

/**
 * @description: 测试局部线程池是否会被回收
 * @author: Winter
 * @time: 2019年12月30日, 0030 17:45
 */
public class ThreadPoolDemo {

    public static void main(String[] args) throws Exception{
        byte[] oneM =new byte[1024*1024];
        ThreadPoolLocal threadPoolLocal = new ThreadPoolLocal();
        threadPoolLocal.testCachedThreadPool();//60s后释放回收
        threadPoolLocal.testFixedThreadPool();//不释放
        threadPoolLocal.testSingleThreadPool();//用完即释放
        System.gc();
        Thread.sleep(60*1000);
    }
}
