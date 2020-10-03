package org.imzdong.study.msb.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞Io
 * 重点就是阻塞accept、read、write，
 * 服务接受端一个线程，一个客户端链接一个线程。
 */
public class BioServer {

    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(9090);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(address);
        for (;;){
            //谨记 一定要放到for循环里面，才中，才能监听到其他的客户端socket
            Socket accept = serverSocket.accept();//阻塞等待客户端链接
            new Thread(()->handler(accept)).start();
        }
    }

    private static void handler(Socket client) {
        String name = Thread.currentThread().getName();
        System.out.println("thread name:"+name+" port "+ client.getPort());
        InputStream inputStream = null;
        try {
            inputStream = client.getInputStream();
            byte[] bytes = new byte[1024];
            for(;;) {//必须需要for循环，如果没有for循环，socket没有释放，照样可以拿到消息，但是程序未处理，直到socket的队列接受满了就丢掉值
                inputStream.read(bytes);//阻塞等待客户端输入
                System.out.println("thread name:" + name + "input:" + new String(bytes));
                OutputStream outputStream = client.getOutputStream();
                outputStream.write(bytes);//阻塞等待客户单接受
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
