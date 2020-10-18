package org.imzdong.study.msb.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolNioServer {

    private static Selector selector;
    private static ExecutorService service = Executors.newFixedThreadPool(10);

    static {
        try {
            selector = Selector.open();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PoolNioServer poolNioServer = new PoolNioServer();
        poolNioServer.init();
        poolNioServer.listen();
    }

    private void listen() {
        while (true){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if(next.isAcceptable()){
                        ServerSocketChannel ssc = (ServerSocketChannel) next.channel();
                        SocketChannel accept = ssc.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    }
                    if(next.isReadable()){
                        next.interestOps(next.interestOps()&(~SelectionKey.OP_READ));
                        service.execute(()->workHandle(next));
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void workHandle(SelectionKey next) {
        String name = Thread.currentThread().getName();
        SocketChannel client = (SocketChannel) next.channel();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        try {
            SocketAddress remoteAddress = client.getRemoteAddress();
            System.out.println(name+":"+remoteAddress);
            client.read(allocate);
            allocate.flip();
            byte[] bytes = new byte[allocate.limit()];
            allocate.get(bytes);
            System.out.println("input : "+new String(bytes));
            allocate.flip();
            client.write(allocate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(9090));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
