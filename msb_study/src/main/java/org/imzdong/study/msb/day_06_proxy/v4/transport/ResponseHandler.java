package org.imzdong.study.msb.day_06_proxy.v4.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ResponseHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackMsg packMsg = (PackMsg) msg;
        ResponseCallBack.runCallBack(packMsg);
    }
}
