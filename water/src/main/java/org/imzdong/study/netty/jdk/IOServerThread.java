package org.imzdong.study.netty.jdk;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/18
 */
public class IOServerThread {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            //主线程接受请求连接,客户端新的一次连接就新建
            Socket accept = serverSocket.accept();
            System.out.println("新建连接");
            int hashCode = accept.hashCode();
            new Thread(() -> {
                try {
                    InputStream inputStream = accept.getInputStream();
                    byte[] box = new byte[1024];
                    int num;
                    String name = Thread.currentThread().getName();
                    while ((num = inputStream.read(box)) != -1) {
                        System.out.println(name+"---"+new String(box, 0, num));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "线程" + hashCode).start();
        }
    }
}
