package org.imzdong.study;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.imzdong.study.msb.day_09_tank_net.model.Dir;
import org.imzdong.study.msb.day_09_tank_net.model.Group;
import org.imzdong.study.msb.day_09_tank_net.net.MsgType;
import org.imzdong.study.msb.day_09_tank_net.net.TankJoinMsg;
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
        TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, false, Group.GOOD, uuid);
        EmbeddedChannel ch = new EmbeddedChannel(new TankMsgEncoder());
        ch.writeOutbound(msg);

        ByteBuf buf = ch.readOutbound();
        MsgType msgType = MsgType.values()[buf.readInt()];
        int bodyLength = buf.readInt();
        byte[] body = new byte[bodyLength];
        buf.readBytes(body);
        TankJoinMsg tankJoinMsg = new TankJoinMsg();
        tankJoinMsg.parse(body);

        Assert.assertTrue(msgType == MsgType.TANK_JOIN && bodyLength == 33);
        buf.release();
        System.out.println(msg);
        System.out.println(tankJoinMsg);
    }

    /**
     * 模拟输入解码decode
     */
    @Test
    public void decode() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        TankJoinMsg msg = new TankJoinMsg(10, 10, Dir.LEFT, false, Group.GOOD, uuid);

        EmbeddedChannel ch = new EmbeddedChannel(new TankMsgEncoder(), new TankMsgDecoder());
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(msg.getMsgType().ordinal());
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        ch.writeInbound(buf.duplicate());

        TankJoinMsg tm = ch.readInbound();
        System.out.println(tm);

        Assert.assertTrue(tm.getX() == 10 && tm.getY() == 10);
        Assert.assertTrue(tm.getDir() == Dir.LEFT && tm.getGroup() == Group.GOOD);
        Assert.assertTrue(tm.getUuid().toString().equals(tm.getUuid().toString()));

    }
}
