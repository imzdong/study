package org.imzdong.study.msb.io.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) throws Exception {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",9090);
        Socket socket = new Socket();
        socket.connect(address);
        OutputStream outputStream = socket.getOutputStream();
        InputStream in = System.in;
        byte[] bytes = new byte[1024];
        for(;;) {
            in.read(bytes);//阻塞的方法
            outputStream.write(bytes);//阻塞的方法
            outputStream.flush();
        }
    }
}
