package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.imzdong.study.msb.day_09_tank_net.TankFrame;
import org.imzdong.study.msb.day_09_tank_net.model.Tank;

import java.util.Objects;
import java.util.UUID;

public class ClientHandler extends SimpleChannelInboundHandler<Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        TankMsg tankMsg = (TankMsg) msg;
        UUID msgUuid = tankMsg.getUuid();
        TankFrame instance = TankFrame.getInstance();
        Tank myTank = instance.getTank();
        if(!Objects.equals(myTank.getUuid(), msgUuid)){
            //不是自己都发一遍，但是不添加自己
            ctx.writeAndFlush(new TankMsg(myTank));
        }
        if(!instance.enemyList.containsKey(msgUuid)) {
            //如果未添加就添加
            Tank tank = new Tank(tankMsg.getX(), tankMsg.getY(), tankMsg.getDir(), instance, tankMsg.getGroup(), msgUuid);
            instance.enemyList.put(tank.getUuid().toString(), tank);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Tank tank = TankFrame.getInstance().getTank();
        ctx.writeAndFlush(new TankMsg(tank));
    }
}
