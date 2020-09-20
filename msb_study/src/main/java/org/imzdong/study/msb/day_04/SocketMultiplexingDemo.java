package org.imzdong.study.msb.day_04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * BIO：阻塞，多连接需要多线程。（线程成本、系统调用成本、阻塞成本）
 * NIO：新IO（非阻塞IO），单线程可支持C10K （轮询调用R/W，没有变更的时候也调用。系统调用成本大）
 * 多路复用（select/poll、epoll）： （一次调用查询R/W，减少系统调用）
 * ----select/poll：用户态一次调用查询R/W变更查询，内核态照常轮询查询多fd的状态是否变更，变更的fd返回。
 * ----epoll：用户态一次查询R/W变更查询，内核态直接返回fd状态变更的fd（网卡给内存空间事件通知时，
 * 开辟新空间维护监控已经创建的红黑树变更的fd为一个数组，在R/W查询时直接返回）
 */
public class SocketMultiplexingDemo {

    public static void main(String[] args) throws Exception{
        //获取server
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(9090));
        server.configureBlocking(false);//非阻塞
        //多路复用器的 复用器 epoll_create
        Selector selector = Selector.open();
        //select、poll维护数组
        //epoll的epoll_ctl
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys : size"+selectionKeys.size());
            // select/poll select()/poll()
            // epoll_wait
            if(selector.select(500)>0){
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();//移出集合，防止重复处理
                    if(next.isAcceptable()){
                        //处理连接（三次握手）
                        ServerSocketChannel ssc = (ServerSocketChannel) next.channel();
                        SocketChannel client = ssc.accept();
                        client.configureBlocking(false);
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
                        //epoll epoll_tcl(add,fdListen,fdSocket)
                        client.register(selector, SelectionKey.OP_READ, allocate);
                        //建立连接
                        System.out.println("建立连接，客户端："+client.getRemoteAddress());
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
