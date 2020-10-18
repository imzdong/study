package org.imzdong.study.msb.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;


public class NettyClient {

    public static void main(String[] args) throws Exception{
        EventLoopGroup event = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            //另一个线程启动处理 异步执行
            ChannelFuture connect = bootstrap.group(event)
                    //非阻塞io/阻塞io
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            //Thread.sleep(10000);
                            String name = Thread.currentThread().getName();
                            System.out.println(name+" : "+"init Channel!!!");
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    }).connect("127.0.0.1", 9090);
            connect.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    String name = Thread.currentThread().getName();
                    System.out.println(name+": operationComplete addListener");
                    System.out.println(future);
                }
            });
            connect.sync();//同步等待 ChannelFuture是一个有结果的回调，不设置可能是个未初始化的回调
            String name = Thread.currentThread().getName();
            System.out.println(name+":main ...");
            connect.channel().closeFuture().sync();
        }finally {
            String name = Thread.currentThread().getName();
            System.out.println(name+":shutdownGracefully ... before");
            event.shutdownGracefully();
            System.out.println(name+":shutdownGracefully ... after");
        }

    }
}
class ClientHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server output");
        ByteBuf serverMsg = (ByteBuf) msg;
        try {
            byte[] bytes = new byte[serverMsg.readableBytes()];
            serverMsg.readBytes(bytes);
            System.out.println(new String(bytes));
        }finally {
            if(serverMsg != null){
                // 释放内存
                ReferenceCountUtil.release(msg);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Winter".getBytes());
        ctx.writeAndFlush(byteBuf);//已经释放了，可以回收内存，不会出现内存溢出
    }
}
