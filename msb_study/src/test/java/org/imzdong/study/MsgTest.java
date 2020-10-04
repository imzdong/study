package org.imzdong.study;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.imzdong.study.msb.day_09_tank_net.model.Dir;
import org.imzdong.study.msb.day_09_tank_net.model.Group;
import org.imzdong.study.msb.day_09_tank_net.net.TankMsg;
import org.imzdong.study.msb.day_09_tank_net.net.TankMsgDecoder;
import org.imzdong.study.msb.day_09_tank_net.net.TankMsgEncoder;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class MsgTest {

    /**
     * 模拟输出编码encode
     */
    @Test
    public void encode(){
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        TankMsg msg = new TankMsg(5, 10, Dir.DOWN, false, Group.GOOD, uuid);
        EmbeddedChannel ch = new EmbeddedChannel(new TankMsgEncoder());
        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();

        Assert.assertTrue(x == 5 && y == 10);
        buf.release();
        System.out.println(msg);
    }

    /**
     * 模拟输入解码decode
     */
    @Test
    public void decode() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        TankMsg msg = new TankMsg(10, 10, Dir.LEFT, false, Group.GOOD, uuid);

        EmbeddedChannel ch = new EmbeddedChannel(new TankMsgEncoder(), new TankMsgDecoder());
        ByteBuf buf = Unpooled.buffer();
        //buf.writeInt(msg.getX());
        //buf.writeInt(msg.getY());
        buf.writeBytes(msg.toBytes());
        ch.writeInbound(buf.duplicate());

        TankMsg tm = ch.readInbound();
        System.out.println(tm);

        Assert.assertTrue(tm.getX() == 10 && tm.getY() == 10);
        Assert.assertTrue(tm.getDir() == Dir.LEFT && tm.getGroup() == Group.GOOD);
        Assert.assertTrue(tm.getUuid().toString().equals(tm.getUuid().toString()));

    }
}
