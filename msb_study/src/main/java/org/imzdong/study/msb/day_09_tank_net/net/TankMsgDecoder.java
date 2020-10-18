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
        if(in.readableBytes()<8) return;
        //标记从这里开始读取
        in.markReaderIndex();
        MsgType msgType = MsgType.values()[in.readInt()];
        int bodyLength = in.readInt();
        if(in.readableBytes() < bodyLength){
            //重置到标记位置，下次够了在读取，解决拆包问题
            in.resetReaderIndex();
            return;
        }
        byte[] body = new byte[bodyLength];
        in.readBytes(body);
        switch (msgType){
            case TANK_JOIN:
                TankJoinMsg tankJoinMsg = new TankJoinMsg();
                tankJoinMsg.parse(body);
                out.add(tankJoinMsg);
                break;
            case TANK_MOVING:
                TankMovingMsg tankMovingMsg = new TankMovingMsg();
                tankMovingMsg.parse(body);
                out.add(tankMovingMsg);
            case TANK_STOP:
                TankStopMsg tankStopMsg = new TankStopMsg();
                tankStopMsg.parse(body);
                out.add(tankStopMsg);
            default:
                break;
        }

    }
}
