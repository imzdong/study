package org.imzdong.study.msb.day_06_proxy.v4.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.imzdong.study.msb.day_06_proxy.SerializeUtil;
import org.imzdong.study.msb.day_06_proxy.v4.protocol.MyContent;
import org.imzdong.study.msb.day_06_proxy.v4.protocol.MyHeader;

import java.util.List;

public class MsgDecode extends ByteToMessageDecoder {

    //父类里一定有channelread{  前老的拼buf  decode（）；剩余留存 ;对out遍历 } -> bytebuf
    //因为你偷懒，自己能不能实现！
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        while(buf.readableBytes() >= 124) {
            byte[] bytes = new byte[124];
            buf.getBytes(buf.readerIndex(),bytes);  //从哪里读取，读多少，但是readindex不变
            MyHeader myHeader = SerializeUtil.deserialize(bytes);
            //DECODE在2个方向都使用
            //通信的协议
            if(buf.readableBytes() >= myHeader.getDateLength()){
                //处理指针
                buf.readBytes(124);  //移动指针到body开始的位置
                byte[] data = new byte[myHeader.getDateLength()];
                buf.readBytes(data);
                MyContent content = SerializeUtil.deserialize(data);
                if(myHeader.getFlag() == 0x14141414){
                    out.add(new PackMsg(myHeader,content));
                }else if(myHeader.getFlag() == 0x14141424){
                    out.add(new PackMsg(myHeader,content));
                }
            }else{
                break;
            }
        }
    }
}