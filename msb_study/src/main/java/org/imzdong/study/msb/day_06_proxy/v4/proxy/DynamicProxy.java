package org.imzdong.study.msb.day_06_proxy.v4.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.imzdong.study.msb.day_06_proxy.SerializeUtil;
import org.imzdong.study.msb.day_06_proxy.v4.protocol.MyContent;
import org.imzdong.study.msb.day_06_proxy.v4.protocol.MyHeader;
import org.imzdong.study.msb.day_06_proxy.v4.transport.ClientFactory;
import org.imzdong.study.msb.day_06_proxy.v4.transport.ResponseCallBack;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class DynamicProxy implements InvocationHandler {

    Class<?>  interfaceClass;

    DynamicProxy(Class<?> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //1、通信协议（http://dubbo.apache.org/zh-cn/blog/dubbo-protocol.html）
        // http://dubbo.apache.org/zh-cn/blog/Dubbo-supporting-gRPC-HTTP2-and-protobuf.html
        String name = interfaceClass.getName();
        String methodName = method.getName();
        MyContent myContent = new MyContent();
        myContent.setInterfaceName(name);
        myContent.setMethodName(methodName);
        myContent.setArgs(args);
        //2、序列化
        byte[] contentBytes = SerializeUtil.serialize(myContent);
        MyHeader myHeader = new MyHeader();
        int f = 0x14141414;
        long requestID =  Math.abs(UUID.randomUUID().getLeastSignificantBits());
        myHeader.setDateLength(contentBytes.length);
        myHeader.setRequestId(requestID);
        myHeader.setFlag(f);
        byte[] headerBytes = SerializeUtil.serialize(myHeader);
        //3、网络通信（I/O模型）
        ClientFactory clientFactory = ClientFactory.getClientFactory();
        NioSocketChannel nioSocket = clientFactory.getNioSocket(new InetSocketAddress("127.0.0.1",9090), 5);
        System.out.println("headerBytes size:"+headerBytes.length);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + contentBytes.length);
        byteBuf.writeBytes(headerBytes);
        byteBuf.writeBytes(contentBytes);
        nioSocket.writeAndFlush(byteBuf);
        //4、动态代理
        long id = myHeader.getRequestId();
        CompletableFuture<String> res = new CompletableFuture<>();
        ResponseCallBack.addCallBack(id, res);
        return res.get();
    }

}
