package org.imzdong.study.stone.zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author admin
 * @date 2021/6/15 下午3:37
 */
public class ZkMain {

    public static void main(String[] args) throws Exception{
        String connStr = "127.0.0.1:2181";
        String zkTemp = "/tmp";
        CountDownLatch countDown = new CountDownLatch(1);

        Watcher watcher= event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.err.println("eventType:"+event.getType());
                if(event.getType()== Watcher.Event.EventType.None){
                    countDown.countDown();
                }else if(event.getType()== Watcher.Event.EventType.NodeCreated){
                    System.out.println("listen:节点创建");
                }else if(event.getType()== Watcher.Event.EventType.NodeChildrenChanged){
                    System.out.println("listen:子节点修改");
                }
            }
        };

        ZooKeeper zookeeper = new ZooKeeper(connStr, 5000,watcher);
        countDown.await();

        //注册监听,每次都要重新注册，否则监听不到
        zookeeper.exists(zkTemp, watcher);

        // 创建节点
        String result = zookeeper.create(zkTemp, "Winter".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(result);

        Thread.sleep(10);

        // 获取节点
        byte[] bs = zookeeper.getData(zkTemp, true, null);
        result = new String(bs);
        System.out.println("创建节点后的数据是:" + result);

        // 修改节点
        zookeeper.setData(zkTemp, "Update Winter".getBytes(), -1);

        Thread.sleep(10);

        bs = zookeeper.getData(zkTemp, true, null);
        result = new String(bs);
        System.out.println("修改节点后的数据是:" + result);

        // 删除节点
        zookeeper.delete(zkTemp, -1);
        System.out.println("节点删除成功");
    }
}
