package org.imzdong.study.msb.day_09_tank_net.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * model -> buf 经过msg
 */
public class TankMsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {
        System.out.println("encode...");
        byte[] bytes = msg.toBytes();
        out.writeInt(msg.getMsgType().ordinal());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
