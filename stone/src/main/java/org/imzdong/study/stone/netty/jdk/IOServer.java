package org.imzdong.study.stone.netty.jdk;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: 服务端
 * @author: Winter
 * @time: 2020/1/17
 */
public class IOServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        byte[] box = new byte[1024];
        int num;
        while((num=inputStream.read(box))!=-1){
            System.out.println(num);
            System.out.println(new String(box,0,num));
        }
        OutputStream outputStream = accept.getOutputStream();
        String resp = "收到！";
        outputStream.write(resp.getBytes("UTF-8"));
        inputStream.close();
        outputStream.flush();
        outputStream.close();
        accept.close();
        serverSocket.close();
    }
}
