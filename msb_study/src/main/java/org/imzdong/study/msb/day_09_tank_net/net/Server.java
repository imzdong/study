package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

    private final static Server server = new Server();
    public final static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Server(){}
    public static Server getInstance(){
        return server;
    }

    public void startSync(int port){
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup(2);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            ChannelFuture sync = serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new TankMsgEncoder())
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new ServerHandler());
                        }
                    })
                    .bind("127.0.0.1", port).sync();

            //阻塞等待
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
