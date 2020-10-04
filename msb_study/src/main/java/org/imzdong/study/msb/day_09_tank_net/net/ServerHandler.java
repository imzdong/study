package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        ServerFrame.getInstance().setTextArea(socketAddress+": connected!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server input");
        Server.clients.writeAndFlush(msg);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Server.clients.remove(ctx.channel());
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        ServerFrame.getInstance().setTextArea(socketAddress+": channelUnregistered!");
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Server.clients.remove(ctx.channel());
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        ServerFrame.getInstance().setTextArea(socketAddress+": exceptionCaught!");
        ctx.close();
    }
}
