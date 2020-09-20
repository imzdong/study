package org.imzdong.study.msb.day_04.netty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class NioLoopThread implements Runnable{

    Selector selector;
    LinkedBlockingQueue<SelectableChannel> lbq;

    public NioLoopThread() {
        try {
            selector = Selector.open();
            lbq = new LinkedBlockingQueue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                //查看fd变更数量，阻塞执行
                String name = Thread.currentThread().getName();
                System.out.println(name + ":before ....."+selector.selectedKeys().size());
                int nums = selector.select();
                System.out.println(name + ":after ....."+selector.selectedKeys().size());
                if(nums > 0){
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey next = iterator.next();
                        iterator.remove();
                        if(next.isAcceptable()){
                            acceptHandler(next);
                        }else if(next.isReadable()){
                            System.out.println("read...");
                        }
                    }
                }else if(nums == 0){
                    //break;
                }else {
                    System.out.println("fd 关闭...");
                    break;
                }
                if(!lbq.isEmpty()){
                    SelectableChannel take = lbq.take();
                    if(take instanceof  ServerSocketChannel){
                        ServerSocketChannel ssc = (ServerSocketChannel) take;
                        ssc.register(selector, SelectionKey.OP_ACCEPT);
                    }else if(take instanceof SocketChannel){
                        SocketChannel client = (SocketChannel) take;
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        client.register(selector, SelectionKey.OP_READ, buffer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptHandler(SelectionKey next) {
        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
        try {
            SocketChannel client = channel.accept();
            client.configureBlocking(false);
            client.register(selector,SelectionKey.OP_READ);
            System.out.println("acceptHandler:"+client.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
