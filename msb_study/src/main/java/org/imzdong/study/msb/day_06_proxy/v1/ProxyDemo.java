package org.imzdong.study.msb.day_06_proxy.v1;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.imzdong.study.msb.day_06_proxy.SerializeUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: Winter
 * @time: 2020年9月25日, 0025 17:15
 */
public class ProxyDemo {

    public static void main(String[] args) throws InterruptedException {
        new Thread(ProxyDemo::server).start();
        Thread.sleep(1000);
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                Random random = new Random();
                String name = Thread.currentThread().getName();
                client(name+":"+"香蕉"+random.nextInt(20));
            }).start();

        }
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


    private static void client(String args) {
        Animal animal = getProxy(Animal.class);
        System.out.println("client finished:"+animal.eat(args));
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
        //1、通信协议（http://dubbo.apache.org/zh-cn/blog/dubbo-protocol.html）
        // http://dubbo.apache.org/zh-cn/blog/Dubbo-supporting-gRPC-HTTP2-and-protobuf.html
        String name = interfaceClass.getName();
        String methodName = method.getName();
        System.out.println(String.format("real class name：%s，method：%s，parmas：%s", name , methodName, args[0]));
        MyContent myContent = new MyContent();
        myContent.setInterfaceName(name);
        myContent.setMethodName(methodName);
        myContent.setArgs(args);
        //2、序列化
        byte[] contentBytes = SerializeUtil.serialize(myContent);
        MyHeader myHeader = new MyHeader();
        int f = 0x14141414;
        long requestID =  Math.abs(UUID.randomUUID().getLeastSignificantBits());
        myHeader.setRequestId(requestID);
        myHeader.setDateLength(contentBytes.length);
        byte[] headerBytes = SerializeUtil.serialize(myHeader);
        //3、网络通信（I/O模型）
        ClientFactory clientFactory = ClientFactory.getClientFactory();
        NioSocketChannel nioSocket = clientFactory.getNioSocket(new InetSocketAddress("127.0.0.1", 9090), 1);
        System.out.println("headerBytes size:"+headerBytes.length);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + contentBytes.length);
        byteBuf.writeBytes(headerBytes);
        byteBuf.writeBytes(contentBytes);
        nioSocket.writeAndFlush(byteBuf);
        //4、动态代理
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ResponseCallBack.putRequest(requestID+"",countDownLatch);
        System.out.println("client await...");
        countDownLatch.await();
        System.out.println("client continue...");
        return ResponseCallBack.getRes(requestID+"");
    }

}

class ClientFactory{

    private static ClientFactory clientFactory = new ClientFactory();

    private ClientFactory(){}

    public synchronized static ClientFactory getClientFactory(){
        return clientFactory;
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
        long requestId = 0;
        String arg = "";
        if(buf.readableBytes() >= 115){
            byte[] headers = new byte[115];
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
        String str = (String) charSequence;
        String[] res = str.split(",");
        String requestId = res[0];
        ResponseCallBack.callBack(requestId,str);
    }
}
class ResponseCallBack{
    private static ConcurrentHashMap<String, CountDownLatch> requestMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> resMap = new ConcurrentHashMap<>();

    static void putRequest(String requestId, CountDownLatch countDownLatch){
        requestMap.put(requestId, countDownLatch);
    }

    static void callBack(String requestId, String res){
        CountDownLatch countDownLatch = requestMap.get(requestId);
        countDownLatch.countDown();
        resMap.put(requestId, res);
    }

    static String getRes(String requestId){
        return resMap.get(requestId);
    }
}

class MyHeader implements Serializable {
    int flag;
    int dateLength;
    long requestId;

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

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
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

     String eat(String food);
}
