package org.imzdong.study.msb.netty.n1_chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class NettyClient {

    private Channel channel;

    public void connect(){
        EventLoopGroup work = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture connect = bootstrap.group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    }).connect("127.0.0.1", 9090);
            connect.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        System.out.println("channel success");
                        channel = future.channel();
                    }else {
                        System.out.println("获取channel异常");
                    }
                }
            });
            //等待连接
            connect.sync();
            //等待关闭
            connect.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg){
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes());
        System.out.println(channel);
        channel.writeAndFlush(byteBuf);
    }

    public void close() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("byb".getBytes());
        channel.writeAndFlush(byteBuf);
    }
}
class ClientHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName()+":channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client read");
        ClientChat instance = ClientChat.getInstance();
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        instance.updateTxt(new String(bytes));
        ReferenceCountUtil.release(buf);
    }
}
