package org.imzdong.study.msb.day_05;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyDemo {

    public static void main(String[] args) throws Exception{
        server();
    }

    private static void server() throws InterruptedException {
        EventLoopGroup server = new NioEventLoopGroup(1);
        ServerSocketChannel ssc = new NioServerSocketChannel();
        ChannelPipeline pipeline = ssc.pipeline();
        pipeline.addLast(new MyAcceptHandler(server,new MyReadHandler()));
        server.register(ssc);
        ChannelFuture bind = ssc.bind(new InetSocketAddress(9090));
        bind.channel().closeFuture().sync();
    }

}

class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    EventLoopGroup server;
    ChannelHandler channelHandler;

    MyAcceptHandler(EventLoopGroup server, ChannelHandler channelHandler){
        this.server = server;
        this.channelHandler = channelHandler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("register...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel socketChannel = (SocketChannel)msg;
        System.out.println("connection:"+socketChannel.remoteAddress());
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(channelHandler);
        server.register(socketChannel);
    }
}

class MyReadHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read register...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel socketChannel = (SocketChannel)msg;
        System.out.println("read:"+socketChannel.remoteAddress());
    }
}
