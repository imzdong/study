package org.imzdong.study.msb.day_04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器
 */
public class SocketMultiplexing {

    public static void main(String[] args) throws Exception{
        //1、初始化serverSocket并绑定选择器
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(9090));
        server.configureBlocking(false);
        Selector selector = Selector.open();//选择器
        server.register(selector, SelectionKey.OP_ACCEPT);
        //2、
        while (true){
            Set<SelectionKey> keys = selector.keys();
            System.out.println(keys.size()+"   size");
            while (selector.select(500) > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if(next.isAcceptable()){
                        //三次握手建立连接
                        ServerSocketChannel ssc = (ServerSocketChannel) next.channel();
                        SocketChannel accept = ssc.accept();
                        accept.configureBlocking(false);//非阻塞
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        accept.register(selector,SelectionKey.OP_READ,buffer);
                        System.out.println("-------------------------------------------");
                        System.out.println("新客户端：" + accept.getRemoteAddress());
                        System.out.println("-------------------------------------------");
                    }else if(next.isReadable()){
                        SocketChannel client = (SocketChannel) next.channel();
                        ByteBuffer buffer = (ByteBuffer) next.attachment();
                        buffer.clear();
                        int read = 0;
                        try {
                            while (true) {
                                read = client.read(buffer);
                                if (read > 0) {
                                    buffer.flip();
                                    while (buffer.hasRemaining()) {
                                        client.write(buffer);
                                    }
                                    buffer.clear();
                                } else if (read == 0) {
                                    break;
                                } else {
                                    client.close();
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }
}
