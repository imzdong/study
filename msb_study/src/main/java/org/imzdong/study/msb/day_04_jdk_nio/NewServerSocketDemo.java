package org.imzdong.study.msb.day_04_jdk_nio;

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
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(9090));
        server.configureBlocking(false);//非阻塞
        List<SocketChannel> list = new LinkedList<>();
        while (true){
            SocketChannel client = server.accept();
            if(client == null){
                //System.out.println("null....");
            }else {
                client.configureBlocking(false);//和客户端建立的连接非阻塞
                System.out.println("server connect:"+client.getRemoteAddress());
                list.add(client);
            }
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            for(SocketChannel sc:list){
                //System.out.println("server read:"+sc.getRemoteAddress());
                int read = sc.read(allocate);//>0  -1  0   //不会阻塞
                //System.out.println("server read length:"+read);
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
