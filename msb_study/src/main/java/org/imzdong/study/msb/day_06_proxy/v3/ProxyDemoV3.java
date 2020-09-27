package org.imzdong.study.msb.day_06_proxy.v3;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.imzdong.study.msb.day_06_proxy.SerializeUtil;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: Winter
 * @time: 2020年9月25日, 0025 17:15
 */
public class ProxyDemoV3 {

    public static void main(String[] args) throws InterruptedException {
        new Thread(ProxyDemoV3::server).start();
        System.out.println("======================================================");
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
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MsgDecode());
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
        System.out.println(args+":client finished:"+animal.eat(args));
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
        //System.out.println(String.format("real class name：%s，method：%s，parmas：%s", name , methodName, args[0]));
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
        System.out.println("nioSocket:"+nioSocket);
        //System.out.println("headerBytes size:"+headerBytes.length);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerBytes.length + contentBytes.length);
        byteBuf.writeBytes(headerBytes);
        byteBuf.writeBytes(contentBytes);
        nioSocket.writeAndFlush(byteBuf);
        //4、动态代理
        long id = myHeader.getRequestId();
        CompletableFuture<String> res = new CompletableFuture<>();
        ResponseCallBack.addCallBack(id, res);
        System.out.println("client await...");
        return res.get();
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

    private static NioSocketChannel creatClientSocket(InetSocketAddress inetSocketAddress, ClientPool clientPool,
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
                            pipeline.addLast(new MsgDecode());
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

class MsgDecode extends ByteToMessageDecoder {

    //父类里一定有channelread{  前老的拼buf  decode（）；剩余留存 ;对out遍历 } -> bytebuf
    //因为你偷懒，自己能不能实现！
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        while(buf.readableBytes() >= 115) {
            byte[] bytes = new byte[115];
            buf.getBytes(buf.readerIndex(),bytes);  //从哪里读取，读多少，但是readindex不变
            MyHeader myHeader = SerializeUtil.deserialize(bytes);
            //DECODE在2个方向都使用
            //通信的协议
            if(buf.readableBytes() >= myHeader.getDateLength()){
                //处理指针
                buf.readBytes(115);  //移动指针到body开始的位置
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

class ServerRequestHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("server...register...");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackMsg packMsg = (PackMsg) msg;
        String ioName = Thread.currentThread().getName();
        //1,直接在当前方法 处理IO和业务和返回
        //3，自己创建线程池
        //2,使用netty自己的eventloop来处理业务及返回
        //ctx.executor().execute(()->{});
        ctx.executor().parent().next().execute(()->{
            MyContent myContent = packMsg.getMyContent();
            String workName = Thread.currentThread().getName();
            String res = ioName+":"+workName+":"+myContent.getArgs()[0];
            myContent.setRes(res);
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

class ResponseHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackMsg packMsg = (PackMsg) msg;
        ResponseCallBack.runCallBack(packMsg);
    }
}

class ResponseCallBack{

    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestID,CompletableFuture cb){
        mapping.putIfAbsent(requestID,cb);
    }
    public static void runCallBack(PackMsg msg){
        CompletableFuture cf = mapping.get(msg.getMyHeader().getRequestId());
        cf.complete(msg.getMyContent().getRes());
        removeCB(msg.getMyHeader().getRequestId());
    }

    private static void removeCB(long requestID) {
        mapping.remove(requestID);
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
    String res;

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

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "MyContent{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", res='" + res + '\'' +
                '}';
    }
}

interface Animal{

     String eat(String food);
}
