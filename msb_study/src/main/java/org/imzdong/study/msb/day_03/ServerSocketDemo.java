package org.imzdong.study.msb.day_03;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket accept = serverSocket.accept();
            new Thread(()->{
                try {
                    InputStream inputStream = accept.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    bufferedInputStream.read()
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
