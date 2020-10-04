package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.imzdong.study.msb.day_09_tank_net.model.Dir;
import org.imzdong.study.msb.day_09_tank_net.model.Group;

import java.util.List;
import java.util.UUID;

/**
 * buf -> model 经过decode
 */
public class TankMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode...");
        if(in.readableBytes()<33) return;
        int x = in.readInt();
        int y = in.readInt();
        Dir dir = Dir.values()[in.readInt()];
        boolean moving = in.readBoolean();
        Group group = Group.values()[in.readInt()];
        UUID uuid = new UUID(in.readLong(), in.readLong());
        out.add(new TankMsg(x, y, dir, moving, group, uuid));
    }
}
