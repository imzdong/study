package org.imzdong.study.netty.jdk;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/18
 */
public class IOClientThread {
    /**
     * 会出现粘包拆包现象：客户端发送三被tcp合成两个
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        for(int num=0;num<5;num++) {
            new Thread(() -> {
                try {
                    Socket socket = new Socket("127.0.0.1", 8000);
                    OutputStream outputStream = socket.getOutputStream();
                    int sendNum = 0;
                    String name = Thread.currentThread().getName();
                    while (sendNum<2000) {
                        outputStream.write((name+"--客户端发送消息"+sendNum).getBytes("UTF-8"));
                        sendNum++;
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "Winter"+num).start();
        }
    }
}
