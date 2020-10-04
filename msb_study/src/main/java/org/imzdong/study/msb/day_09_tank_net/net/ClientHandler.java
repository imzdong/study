package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.imzdong.study.msb.day_09_tank_net.TankFrame;
import org.imzdong.study.msb.day_09_tank_net.model.Tank;

import java.util.Objects;

public class ClientHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        TankMsg tankMsg = (TankMsg) msg;
        System.out.println("client channelRead..."+tankMsg);
        String msgUuid = tankMsg.getUuid().toString();
        TankFrame instance = TankFrame.getInstance();
        Tank myTank = instance.getTank();
        String myUuid = myTank.getUuid().toString();
        if(Objects.equals(myUuid, msgUuid) || instance.enemyList.containsKey(msgUuid)){
            return;
        }
        //不是自己都发一遍，但是不添加自己
        ctx.writeAndFlush(new TankMsg(myTank));
        //如果未添加就添加
        Tank tank = new Tank(tankMsg.getX(), tankMsg.getY(), tankMsg.getDir(), instance, tankMsg.getGroup(), tankMsg.getUuid());
        instance.enemyList.put(tank.getUuid().toString(), tank);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Tank tank = TankFrame.getInstance().getTank();
        ctx.writeAndFlush(new TankMsg(tank));
    }
}
