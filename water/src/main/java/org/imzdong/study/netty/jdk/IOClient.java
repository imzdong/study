package org.imzdong.study.netty.jdk;

import java.io.InputStream;
import java.net.Socket;

/**
 * @description: 客户端
 * @author: Winter
 * @time: 2020/1/17
 */
public class IOClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1",8000);
        String box = "Hello World";
        socket.getOutputStream().write(box.getBytes("UTF-8"));
        socket.shutdownOutput();//完成发送，提示服务端不在发送消息
        //Thread.sleep(3000);
        InputStream inputStream = socket.getInputStream();
        byte[] inputBox = new byte[1024];
        int num;
        while ((num=inputStream.read(inputBox)) != -1){
            System.out.println(new String(inputBox,0,num));
        }
        inputStream.close();
        socket.close();
    }
}
