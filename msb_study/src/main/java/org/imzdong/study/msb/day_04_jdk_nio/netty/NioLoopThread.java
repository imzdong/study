package org.imzdong.study.msb.day_04_jdk_nio.netty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class NioLoopThread implements Runnable{

    Selector selector;
    LinkedBlockingQueue<SelectableChannel> lbq;
    NioLoopThreadGroup group;

    public NioLoopThread(NioLoopThreadGroup group) {
        try {
            selector = Selector.open();
            lbq = new LinkedBlockingQueue();
            this.group = group;
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
                int nums = selector.select();//阻塞等待，等待变更的fd文件
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
                            readHandler(next);
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
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHandler(SelectionKey next) {
        String name = Thread.currentThread().getName();
        ByteBuffer buffer = (ByteBuffer) next.attachment();
        System.out.println("read buffer:"+buffer);
        SocketChannel client = (SocketChannel) next.channel();
        while (true){
            try {
                int read = client.read(buffer);
                if(read > 0) {
                    buffer.flip();
                    byte[] say = new byte[buffer.remaining()];
                    buffer.get(say);
                    String sayStr = new String(say);
                    System.out.println("thread："+name+"："+sayStr);
                    System.out.println("thread："+name+"：client.read:" + buffer);
                    client.write(buffer);
                    buffer.clear();
                    if("close".equals(sayStr)){
                        client.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void acceptHandler(SelectionKey next) {
        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
        try {
            SocketChannel client = channel.accept();
            client.configureBlocking(false);
            NioLoopThread nlt = this.group.nextNlt();
            nlt.lbq.put(client);
            nlt.selector.wakeup();
            //client.register(nlt.selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
            System.out.println("acceptHandler:"+client.getRemoteAddress());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
