package org.imzdong.study.netty.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/18
 */
public class NioServer {

    public static void main(String[] args) throws Exception{
        Selector serverSelector = Selector.open();
        Selector serverDataSelector = Selector.open();
        new Thread(()->{
            try {
                //启动服务
                ServerSocketChannel ssc = ServerSocketChannel.open();
                ssc.socket().bind(new InetSocketAddress(8000));
                ssc.configureBlocking(false);
                ssc.register(serverSelector, SelectionKey.OP_ACCEPT);
                while (true) {
                    //接受连接
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeys = serverSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            try {
                                SelectionKey next = iterator.next();
                                if (next.isAcceptable()) {
                                    // (1) 每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
                                    SocketChannel clientChannel = ((ServerSocketChannel) next.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(serverDataSelector, SelectionKey.OP_READ);
                                }
                            }finally {
                                iterator.remove();
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },"startServer").start();

        new Thread(()->{
            try {
                while (true) {
                    // (2) 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
                    if (serverDataSelector.select(1) > 0) {
                        Set<SelectionKey> set = serverDataSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = set.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            if (key.isReadable()) {
                                try {
                                    SocketChannel clientChannel = (SocketChannel) key.channel();
                                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                    // (3) 面向 Buffer
                                    clientChannel.read(byteBuffer);
                                    byteBuffer.flip();
                                    System.out.println(Charset.forName("UTF-8").newDecoder().decode(byteBuffer)
                                            .toString());
                                } finally {
                                    keyIterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }

                        }
                    }
                }
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        },"ServerData").start();
    }
}
