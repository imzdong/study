package org.imzdong.study.msb.day_03_jdk_bio;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class SocketClientDemo {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1",9090);
        String name = Thread.currentThread().getName();
        System.out.println(name+"发起请求");
        InputStream in = System.in;
        while (true) {
            System.out.println(name+"疯狂写入");
            clientRead(socket);
            if(clientWrite(socket, name, in)){
                break;
            };
        }
    }

    private static boolean clientWrite(Socket socket,String name, InputStream in) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String input = reader.readLine();//阻塞
        System.out.println(name+":"+input);
        input+="\r";
        if(input.startsWith("close")){
            System.out.println("client close");
            outputStream.write(input.getBytes());
            outputStream.close();
            socket.close();
            return true;
        }
        outputStream.write(input.getBytes());
        outputStream.flush();
        return false;
    }

    private static void clientRead(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader serverRead = new BufferedReader(new InputStreamReader(inputStream));
        if(serverRead.ready()){
            System.out.println("client read:"+serverRead.readLine());//阻塞
        }
    }
}
