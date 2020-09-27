package org.imzdong.study.msb.day_06_proxy.v4;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.imzdong.study.msb.day_06_proxy.SerializeUtil;
import org.imzdong.study.msb.day_06_proxy.v4.proxy.SimpleProxy;
import org.imzdong.study.msb.day_06_proxy.v4.service.Animal;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 服务端处理
 * @author: Winter
 * @time: 2020年9月25日, 0025 17:15
 */
public class ProxyTest {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Random random = new Random();
                String name = Thread.currentThread().getName();
                client(name+":"+"香蕉"+random.nextInt(20));
            }).start();

        }
    }

    private static void client(String args) {
        Animal animal = SimpleProxy.getProxy(Animal.class);
        System.out.println(args+":client finished:"+animal.eat(args));
    }


}

























