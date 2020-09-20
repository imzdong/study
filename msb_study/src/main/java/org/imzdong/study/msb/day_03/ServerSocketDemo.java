package org.imzdong.study.msb.day_03;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ServerSocketDemo {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket accept = serverSocket.accept();
            new Thread(()->{
                try {
                    InputStream inputStream = accept.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    while (true){
                        String s = reader.readLine();
                        if(s != null) {
                            System.out.println(accept.getPort()+":"+s);
                        }
                        if(Objects.equals(s,"close")){
                            break;
                        }
                    }
                    accept.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
