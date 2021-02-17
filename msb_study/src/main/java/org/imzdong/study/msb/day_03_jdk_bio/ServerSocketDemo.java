package org.imzdong.study.msb.day_03_jdk_bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ServerSocketDemo {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            //阻塞等待
            Socket server = serverSocket.accept();
            new Thread(()->{
                try {
                    String name = Thread.currentThread().getName();
                    System.out.println(name+ ":" + server.getPort()+":建立连接");
                    //接受输入
                    InputStream inputStream = server.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    while (true) {
                        if (reader.ready()) {
                            String s = reader.readLine();//阻塞！！！！！
                            if (s != null) {
                                System.out.println(server.getPort() + ":" + s);
                                OutputStream outputStream = server.getOutputStream();
                                outputStream.write(("server res:"+s+"\r").getBytes());
                                outputStream.flush();
                                if (s.startsWith("close")) {
                                    System.out.println(name+ ":" + server.getPort()+":连接close");
                                    inputStream.close();
                                    outputStream.close();
                                    break;
                                }
                            }
                        }
                    }
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
