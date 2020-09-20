package org.imzdong.study.msb.day_04;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * jdk nio新io
 * 操作系统 nio 非阻塞io
 * java新io展示操作系统的非阻塞io
 */
public class NewServerSocketDemo {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel open = ServerSocketChannel.open();
        open.bind(new InetSocketAddress(9090));
        open.configureBlocking(false);//非阻塞
        List<SocketChannel> list = new LinkedList<>();
        while (true){
            Thread.sleep(1000);
            SocketChannel accept = open.accept();
            if(accept == null){
                System.out.println("null....");
            }else {
                accept.configureBlocking(false);
                list.add(accept);
            }
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            for(SocketChannel sc:list){
                int read = sc.read(allocate);//>0  -1  0   //不会阻塞
                if(read > 0){
                    allocate.flip();
                    byte[] dist = new byte[allocate.limit()];
                    allocate.get(dist);
                    System.out.println(new String(dist));
                }
            }
        }

    }
}
