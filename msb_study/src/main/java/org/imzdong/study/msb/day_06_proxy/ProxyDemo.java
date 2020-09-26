package org.imzdong.study.msb.day_06_proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Winter
 * @time: 2020年9月25日, 0025 17:15
 */
public class ProxyDemo {

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            server();
        }).start();
        Thread.sleep(1000);
        client();
    }

    private static void server() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup(2);
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture bind = bootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println(ch.remoteAddress());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ServerRequestHandler());
                    }
                }).bind(new InetSocketAddress("127.0.0.1",9090));
        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void client() {
        Animal animal = getProxy(Animal.class);
        System.out.println(animal.eat("肉"));
    }

    private static <T>T getProxy(Class<T> interfaceClass){
        ClassLoader loader = interfaceClass.getClassLoader();
        Class<?> [] interfaces = {interfaceClass};
        return (T)Proxy.newProxyInstance(loader, interfaces, new DynamicProxy(interfaceClass));
    }
}


class DynamicProxy implements InvocationHandler{

    Class<?>  interfaceClass;

    DynamicProxy(Class<?> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //1、序列化数据
        String name = interfaceClass.getName();
        String methodName = method.getName();
        System.out.println(String.format("real class name：%s，method：%s，parmas：%s", name , methodName, args[0]));
        MyContent myContent = new MyContent();
        myContent.setInterfaceName(name);
        myContent.setMethodName(methodName);
        myContent.setArgs(args);
        byte[] contentBytes = SerializeUtil.serialize(myContent);
        MyHeader myHeader = new MyHeader();
        myHeader.setRequestId(1);
        myHeader.setDateLength(contentBytes.length);
        byte[] headerBytes = SerializeUtil.serialize(myHeader);
        //2、获取Socket连接
        ClientFactory clientFactory = ClientFactory.getClientFactory();
        NioSocketChannel nioSocket = clientFactory.getNioSocket(new InetSocketAddress("127.0.0.1", 9090), 1);
        System.out.println("headerBytes size:"+headerBytes.length);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + contentBytes.length);
        byteBuf.writeBytes(headerBytes);
        byteBuf.writeBytes(contentBytes);
        nioSocket.writeAndFlush(byteBuf);


        return 1;
    }

}

class ClientFactory{

    private ClientFactory(){}

    public synchronized static ClientFactory getClientFactory(){
        return new ClientFactory();
    }

    private static ConcurrentHashMap<InetSocketAddress, ClientPool> pools = new ConcurrentHashMap();

    public synchronized NioSocketChannel getNioSocket(InetSocketAddress inetSocketAddress, int coreSize){
        ClientPool clientPool = pools.get(inetSocketAddress);
        if(clientPool == null){
            pools.putIfAbsent(inetSocketAddress,new ClientPool(coreSize));
            clientPool = pools.get(inetSocketAddress);
        }
        Random random = new Random();
        int nextInt = random.nextInt(coreSize);
        NioSocketChannel nsc = clientPool.nscs[nextInt];
        if(nsc != null ){
            return nsc;
        }
        return clientPool.nscs[nextInt] = creatClientSocket(inetSocketAddress,clientPool, nextInt, coreSize);
    }

    private static NioSocketChannel creatClientSocket(InetSocketAddress inetSocketAddress,ClientPool clientPool,
                                                      int nextInt, int coreSize) {
        Object lock = clientPool.locks[nextInt];
        synchronized (lock){
            NioEventLoopGroup executors = new NioEventLoopGroup(coreSize);
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture connect = bootstrap.group(executors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ResponseHandler());
                        }
                    })
                    .connect(inetSocketAddress);
            try {
                NioSocketChannel channel = (NioSocketChannel)connect.sync().channel();
                return channel;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

class ClientPool{
    int poolSize;
    NioSocketChannel[] nscs;
    Object[] locks;
    ClientPool(int poolSize){
        this.poolSize = poolSize;
        nscs = new NioSocketChannel[poolSize];
        locks = new Object[poolSize];
        for (int i = 0; i < poolSize; i++) {
            locks[i] = new Object();
        }
    }
}

class ServerRequestHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server...register...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server...");
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("server readableBytes..."+buf.readableBytes());
        int requestId = 0;
        String arg = "";
        if(buf.readableBytes() >= 108){
            byte[] headers = new byte[108];
            buf.readBytes(headers);
            MyHeader myHeader = SerializeUtil.deserialize(headers);
            requestId = myHeader.requestId;
            System.out.println(myHeader);
            if(buf.readableBytes()>=myHeader.dateLength){
                byte[] contents = new byte[myHeader.dateLength];
                buf.readBytes(contents);
                MyContent myContent = SerializeUtil.deserialize(contents);
                System.out.println(myContent);
                arg = myContent.getArgs()[0].toString();
            }else {
                System.out.println("数据包："+buf.readableBytes());
            }
            buf.clear();
            String res = requestId+",我接受到了,"+arg;
            buf.writeBytes(res.getBytes("UTF-8"));
            ctx.writeAndFlush(buf);
        }

    }
}

class ResponseHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client response...");
        ByteBuf buf = (ByteBuf) msg;
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), Charset.defaultCharset());
        System.out.println("client response..."+charSequence);

    }
}

class MyHeader implements Serializable {
    int flag;
    int dateLength;
    int requestId;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getDateLength() {
        return dateLength;
    }

    public void setDateLength(int dateLength) {
        this.dateLength = dateLength;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "MyHeader{" +
                "flag=" + flag +
                ", dateLength=" + dateLength +
                ", requestId=" + requestId +
                '}';
    }
}

class MyContent implements Serializable{
    String interfaceName;
    String methodName;
    Object[] args;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "MyContent{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}

interface Animal{

     int eat(String food);
}
