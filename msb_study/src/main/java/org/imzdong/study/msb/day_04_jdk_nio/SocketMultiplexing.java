package org.imzdong.study.msb.day_04_jdk_nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用I/O模型
 * 一个selector，多个事件注册到一个selector
 */
public class SocketMultiplexing {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel server = ServerSocketChannel.open();
        Selector selector = Selector.open();
        server.bind(new InetSocketAddress("127.0.0.1",9090));
        server.configureBlocking(false);//非阻塞
        server.register(selector,SelectionKey.OP_ACCEPT);//注册监听器
        //死循环查看selector的状态变化
        //for (;;){
            multiplexing(selector);
        //}

    }

    private static void multiplexing(Selector selector) throws IOException {
        while (selector.select()>0) {//永久阻塞
            Set<SelectionKey> keys = selector.keys();
            System.out.println("keys:"+keys.size());
            if (keys.size() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    SelectableChannel channel = next.channel();
                    if(next.isAcceptable()){
                        System.out.println("accept...");
                        ServerSocketChannel ssc = (ServerSocketChannel) channel;
                        SocketChannel client = ssc.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    }else if (next.isReadable()){
                        System.out.println("read...");
                        SocketChannel client = (SocketChannel) channel;
                        System.out.println("read client:"+client);
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
                        int read = client.read(allocate);
                        System.out.println("read byteBuffer:"+allocate);
                        if(read >0 ){
                            allocate.flip();
                            byte[] bytes = new byte[allocate.limit()];
                            allocate.get(bytes);
                            System.out.println("read content："+new String(bytes));
                            client.register(selector, SelectionKey.OP_WRITE, allocate);
                        }
                    }else if(next.isWritable()){
                        //next.cancel();
                        System.out.println("write...");
                        SocketChannel client = (SocketChannel) channel;
                        ByteBuffer attachment = (ByteBuffer) next.attachment();
                        attachment.flip();
                        byte[] bytes = new byte[attachment.limit()];
                        attachment.get(bytes);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);;
                        System.out.println("write client:"+client);
                        byteBuffer.put(bytes,0,bytes.length-1);
                        byteBuffer.put(":reply:ok\n".getBytes());
                        byteBuffer.flip();
                        client.write(byteBuffer);
                        byteBuffer.clear();
                        client.register(selector, SelectionKey.OP_READ);
                    }
                }
            }
        }
    }
}
