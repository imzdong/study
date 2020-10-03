package org.imzdong.study.msb.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(9090);
        server.bind(address);
        server.configureBlocking(false);//非阻塞
        Selector selector = Selector.open();
        //告诉大管家（selector），server这个socket仅仅监听accept事件就ok了
        server.register(selector, SelectionKey.OP_ACCEPT);
        for (;;) {
            selector.select();//阻塞，轮询查询那些事件有变动
            //变动的事件都在keys
            handle(selector);
        }

    }

    private static void handle(Selector selector) {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey next = iterator.next();
            iterator.remove();
            if(next.isAcceptable()){
                //监听者事件处理
                ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                try {
                    SocketChannel client = channel.accept();
                    //设置非阻塞，读写都异步
                    client.configureBlocking(false);
                    //仅仅注册写入事件
                    client.register(selector, SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(next.isReadable()){
                //接受消息事件
                SocketChannel client = (SocketChannel) next.channel();
                ByteBuffer allocate = ByteBuffer.allocate(1024);
                try {
                    SocketAddress remoteAddress = client.getRemoteAddress();
                    client.read(allocate);
                    allocate.flip();
                    byte[] bytes = new byte[allocate.limit()];
                    allocate.get(bytes);
                    System.out.println(remoteAddress+": input : "+new String(bytes));
                    allocate.flip();
                    client.write(allocate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
