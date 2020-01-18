package org.imzdong.study.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/18
 */
public class NettyClient {

    public static void main(String[] args) throws Exception{
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup work = new NioEventLoopGroup();

        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1",8000).channel();

        int num=0;
        while (num<200) {
            channel.writeAndFlush(num++ + ": hello world!");
            Thread.sleep(2000);
        }
    }
}
