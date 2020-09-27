package org.imzdong.study.msb.day_06_proxy.v3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 一个NioEventLoop是一个selector，一个selector可以注册多个时间（监听、读写）
 */
public class GroupDemo {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = boss;
        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind0 = sbs.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerHandler())
                .bind(new InetSocketAddress(9090));
        ChannelFuture bind1 = sbs.childHandler(new ServerHandler1()).bind(new InetSocketAddress(9091));

        try {
            bind0.sync().channel().closeFuture().sync();
            bind1.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
@ChannelHandler.Sharable
class ServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("0:"+ctx.channel().localAddress()+"active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), Charset.forName("UTF-8"));
        System.out.println(charSequence);
    }
}
@ChannelHandler.Sharable
class ServerHandler1 extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("1:"+ctx.channel().localAddress()+"active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), Charset.forName("UTF-8"));
        System.out.println(charSequence);
    }
}
