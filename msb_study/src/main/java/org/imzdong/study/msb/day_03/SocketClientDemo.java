package org.imzdong.study.msb.day_03;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

public class SocketClientDemo {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1",9090);
        OutputStream outputStream = socket.getOutputStream();
        boolean flag = true;
        while (flag) {
            InputStream in = System.in;
            byte[] bytes = new byte[1024];
            int br = in.read(bytes);
            String input = new String(bytes,0,br-1);
            System.out.println(input);
            if(Objects.equals(input,"close")){
                flag = false;
                outputStream.write(bytes);
            }
            outputStream.write(bytes);
        }
    }
}
