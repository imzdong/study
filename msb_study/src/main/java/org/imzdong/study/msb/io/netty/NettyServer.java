package org.imzdong.study.msb.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServer {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) {
        NioEventLoopGroup serverGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup(2);

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            ChannelFuture channel = bootstrap.group(serverGroup, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            System.out.println("initChannel："+ch);
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    }).bind("127.0.0.1", 9090)
                    .sync();//同步等待bind完成
            channel.channel()//获取serverSocket
                    .closeFuture()//获取调取close方法返回的结果
                    .sync();//同步等待
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            serverGroup.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}

class ServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler active");
        NettyServer.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server read...");
        ByteBuf serverMsg = (ByteBuf) msg;
        try {
            byte[] bytes = new byte[serverMsg.readableBytes()];
            //serverMsg.readBytes(bytes);
            serverMsg.getBytes(serverMsg.readerIndex(), bytes);
            System.out.println(new String(bytes));
            //ctx.writeAndFlush(serverMsg);
            NettyServer.clients.writeAndFlush(serverMsg);
        }finally {
            if(serverMsg != null){
                //客户端不用了
            }
        }
    }
}
