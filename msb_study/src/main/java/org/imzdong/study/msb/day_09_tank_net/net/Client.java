package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    private final static Client client = new Client();
    private Client(){}
    public static Client getInstance(){
        return client;
    }

    private Channel channel;

    public Channel getChannel(){
        return channel;
    }

    public void connectSync(int port){
        NioEventLoopGroup work = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture sync = bootstrap.group(work)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new TankMsgEncoder())
                                    .addLast(new TankMsgDecoder())
                                    .addLast(new ClientHandler());
                        }
                    })
                    .connect("127.0.0.1", port)
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if(future.isSuccess()){
                                System.out.println("channel init success");
                                channel = future.channel();
                            }else {
                                System.out.println("获取channel失败!!!");
                            }
                        }
                    }).sync();

            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(Msg msg){
        channel.writeAndFlush(msg);
    }

}
