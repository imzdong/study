package org.imzdong.study.msb.day_06_proxy.v4.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.imzdong.study.msb.day_06_proxy.v4.service.Animal;
import org.imzdong.study.msb.day_06_proxy.v4.service.impl.Cat;
import org.imzdong.study.msb.day_06_proxy.v4.transport.MsgDecode;
import org.imzdong.study.msb.day_06_proxy.v4.transport.ServerRequestHandler;

import java.net.InetSocketAddress;

public class ServerStart {

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.register(Animal.class.getName(), new Cat());
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup(2);
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture bind = bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MsgDecode());
                        pipeline.addLast(new ServerRequestHandler(dispatcher));
                    }
                }).bind(new InetSocketAddress("127.0.0.1",9090));
        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
