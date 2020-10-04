package org.imzdong.study.msb.netty.n1_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NettyServer {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) {
        ChatServer.getInstance();
        NettyServer nettyServer = new NettyServer();
        nettyServer.startServer();
    }

    public void startServer(){
        EventLoopGroup boos = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(1);
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            ChannelFuture sync = bootstrap.group(boos, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    }).bind("127.0.0.1", 9090)
                    .sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class ServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyServer.clients.add(ctx.channel());
        System.out.println("active...");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("register :");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server read");
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String clientMsg = new String(bytes);
        if(Objects.equals(clientMsg, "byb")){
            System.out.println("client close");
            ctx.close();
        }else {
            ChatServer.getInstance().setTextArea(clientMsg);
            ReferenceCountUtil.release(buf);
            ByteBuf byteBuf = Unpooled.copiedBuffer(("Server reply: "+clientMsg).getBytes());
            NettyServer.clients.writeAndFlush(byteBuf);
        }
    }
}
