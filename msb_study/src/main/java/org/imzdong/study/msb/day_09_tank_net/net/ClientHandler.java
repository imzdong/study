package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.imzdong.study.msb.day_09_tank_net.TankFrame;
import org.imzdong.study.msb.day_09_tank_net.model.Tank;

public class ClientHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Tank tank = TankFrame.getInstance().getTank();
        ctx.writeAndFlush(new TankJoinMsg(tank));
    }
}
