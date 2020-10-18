package org.imzdong.study.stone.redis;

import org.imzdong.study.stone.thread.ThreadPoolExecutorUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @description: redis发布/订阅者
 * @author: Winter
 * @time: 2019年12月26日, 0026 18:01
 */
public class RedisCore {

    private final static String subTopic = "subTopicKey";

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = ThreadPoolExecutorUtil.getPool();
        Jedis publishJedis = new Jedis("127.0.0.1", 6379);
        CustomPubSub customPubSub = new CustomPubSub();
        for(int num=0;num<3;num++) {
            poolExecutor.execute(() -> {
                //订阅客户端和发布客户端都仅能是独立的
                Jedis subscribeJedis01 = new Jedis("127.0.0.1", 6379);
                subscribeJedis01.subscribe(customPubSub, subTopic);
            });
        }
        poolExecutor.execute(() -> {
            while (true) {
                publishJedis.publish(subTopic, "我要发布了,你们都说说：" + new Random().nextInt(100));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 自定义的订阅者处理类
     */
    static class CustomPubSub extends JedisPubSub{
        /**
         *
         * @param channel 订阅的频道
         * @param message 订阅的频道发布的消息
         */
        @Override
        public void onMessage(String channel, String message) {
            System.out.println(channel + "：我订阅了消息：" + message);
            if("over".equals(message)) {
                this.unsubscribe(channel);
            }
        }

        /**
         *
         * @param channel 订阅的频道
         * @param subscribedChannels 订阅的频道数
         */
        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println(channel+"：我订阅了："+subscribedChannels);
        }
    }

    /**
     * redis的基本操作
     */
    private static void statisticsTime() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        /*String strKey = "age";
        String strValue = jedis.get(strKey);
        System.out.println(strValue);
        Long name = jedis.scard("name");
        System.out.println(name);*/
        Long count = 100000L+1000001;
        String [] ids = new String [100000];
        long timeMillis = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        Long id = 1000001L;
        for(int i=0;i<100000;i++){
            ids[i] = id.toString();
            id++;
        }
        /*for (Long i = 1000001L; i < count; i++) {
            //pipeline.set("key" + i, "测试pipeline插入数据");
            //pipeline.rpush("Winter",i.toString());//耗时439毫秒
            //pipeline.lpop("Winter");//耗时209毫秒
            //jedis.rpush("Winter",i.toString());//耗时4992毫秒
        }*/
        pipeline.rpush("Winter",ids);//耗时439毫秒
        pipeline.sync();
        long endTime = System.currentTimeMillis();
        System.out.println("耗时" + (endTime - timeMillis) + "毫秒");
    }
}
