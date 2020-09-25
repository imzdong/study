package org.imzdong.study.msb.day_05_netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class NettyDemo {

    public static void main(String[] args) throws Exception{
        server();
        nettyServer();
        client();
        nettyClient();
    }

    private static void nettyServer() {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture future = bootstrap.group(group, group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyReadHandler());
                    }
                }).bind(new InetSocketAddress(9090));
        try {
            future.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void server() throws InterruptedException {
        EventLoopGroup server = new NioEventLoopGroup(1);
        ServerSocketChannel ssc = new NioServerSocketChannel();
        //注册服务端
        server.register(ssc);
        ChannelPipeline pipeline = ssc.pipeline();
        //添加服务端处理
        pipeline.addLast(new MyAcceptHandler(server,new MyReadHandler()));
        ChannelFuture bind = ssc.bind(new InetSocketAddress(9090));
        bind.sync().channel().closeFuture().sync();
    }

    private static void client(){
        NioEventLoopGroup work = new NioEventLoopGroup(1);
        NioSocketChannel client = new NioSocketChannel();
        work.register(client);
        client.pipeline().addLast(new MyReadHandler());
        ChannelFuture connect = client.connect(new InetSocketAddress("127.0.0.1",9090));
        try {
            ChannelFuture sync = connect.sync();
            ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
            ChannelFuture send = client.writeAndFlush(buf);
            send.sync();
            sync.channel().closeFuture().sync();
            System.out.println("client over....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void nettyClient(){
        NioEventLoopGroup work = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture connect = bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyReadHandler());
                    }
                }).connect(new InetSocketAddress("127.0.0.1", 9090));
        try {
            Channel client = connect.sync().channel();
            ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
            ChannelFuture send = client.writeAndFlush(buf);
            send.sync();
            client.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        //添加客户端处理
        pipeline.addLast(channelHandler);
        //注册客户端
        server.register(socketChannel);
    }
}
@ChannelHandler.Sharable
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
        ByteBuf buf = (ByteBuf) msg;
        CharSequence str = buf.getCharSequence(0,buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);
        ctx.writeAndFlush(buf);
    }
}
