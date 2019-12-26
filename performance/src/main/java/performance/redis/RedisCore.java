package performance.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月26日, 0026 18:01
 */
public class RedisCore {

    public static void main(String[] args) {
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
