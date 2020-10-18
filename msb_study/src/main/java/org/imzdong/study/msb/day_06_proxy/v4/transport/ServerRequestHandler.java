package org.imzdong.study.msb.day_06_proxy.v4.transport;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.imzdong.study.msb.day_06_proxy.SerializeUtil;
import org.imzdong.study.msb.day_06_proxy.v4.protocol.MyContent;
import org.imzdong.study.msb.day_06_proxy.v4.protocol.MyHeader;
import org.imzdong.study.msb.day_06_proxy.v4.rpc.Dispatcher;

import java.lang.reflect.Method;

public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    private Dispatcher dispatcher;

    public ServerRequestHandler(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("server...register...");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackMsg packMsg = (PackMsg) msg;
        //String ioName = Thread.currentThread().getName();
        //1,直接在当前方法 处理IO和业务和返回
        //3，自己创建线程池
        //2,使用netty自己的eventloop来处理业务及返回
        //ctx.executor().execute(()->{});
        ctx.executor().parent().next().execute(()->{
            MyContent myContent = packMsg.getMyContent();
            //String workName = Thread.currentThread().getName();
            //String res = ioName+":"+workName+":"+myContent.getArgs()[0];
            Object obj = dispatcher.get(myContent.getInterfaceName());
            Class<?> objClass = obj.getClass();
            String methodName = myContent.getMethodName();
            Method method = null;
            Object invoke = "";
            try {
                method = objClass.getMethod(methodName, myContent.getArgs()[0].getClass());
                invoke = method.invoke(obj, myContent.getArgs());
            } catch (Exception e) {
                e.printStackTrace();
            }
            myContent.setRes(invoke.toString());
            byte[] contentBytes = SerializeUtil.serialize(myContent);
            MyHeader myHeader = packMsg.getMyHeader();
            myHeader.setFlag(0x14141424);
            myHeader.setDateLength(contentBytes.length);
            byte[] headerBytes = SerializeUtil.serialize(myHeader);
            ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + contentBytes.length);
            buf.writeBytes(headerBytes);
            buf.writeBytes(contentBytes);
            ctx.writeAndFlush(buf);
        });
    }
}
