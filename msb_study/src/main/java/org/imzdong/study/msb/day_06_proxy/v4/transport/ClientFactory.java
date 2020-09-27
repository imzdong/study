package org.imzdong.study.msb.day_06_proxy.v4.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ClientFactory{

    private static ClientFactory clientFactory = new ClientFactory();

    private ClientFactory(){}

    public synchronized static ClientFactory getClientFactory(){
        return clientFactory;
    }

    private static ConcurrentHashMap<InetSocketAddress, ClientPool> pools = new ConcurrentHashMap();


    public synchronized NioSocketChannel getNioSocket(InetSocketAddress inetSocketAddress, int coreSize){
        ClientPool clientPool = pools.get(inetSocketAddress);
        if(clientPool == null){
            pools.putIfAbsent(inetSocketAddress,new ClientPool(coreSize));
            clientPool = pools.get(inetSocketAddress);
        }
        Random random = new Random();
        int nextInt = random.nextInt(coreSize);
        NioSocketChannel nsc = clientPool.nscs[nextInt];
        if(nsc != null ){
            return nsc;
        }
        return clientPool.nscs[nextInt] = creatClientSocket(inetSocketAddress,clientPool, nextInt, coreSize);
    }

    private static NioSocketChannel creatClientSocket(InetSocketAddress inetSocketAddress, ClientPool clientPool,
                                                      int nextInt, int coreSize) {
        Object lock = clientPool.locks[nextInt];
        synchronized (lock){
            NioEventLoopGroup executors = new NioEventLoopGroup(coreSize);
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture connect = bootstrap.group(executors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MsgDecode());
                            pipeline.addLast(new ResponseHandler());
                        }
                    })
                    .connect(inetSocketAddress);
            try {
                NioSocketChannel channel = (NioSocketChannel)connect.sync().channel();
                return channel;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
