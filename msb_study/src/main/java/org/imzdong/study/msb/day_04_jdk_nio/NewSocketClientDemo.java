package org.imzdong.study.msb.day_04_jdk_nio;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NewSocketClientDemo {

    public static void main(String[] args) throws Exception {
        SocketChannel client = SocketChannel.open();
        //client.configureBlocking(true);
        client.connect(new InetSocketAddress("127.0.0.1",9090));
        InputStream in = System.in;
        while (true){
            byte[] bytes = new byte[1024];
            int read = in.read(bytes);
            if(read>0){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                byteBuffer.put(bytes);//写
                byteBuffer.flip();//谨记 一定要反转！！！！！！
                while (byteBuffer.hasRemaining()) {
                    client.write(byteBuffer);
                }
            }
        }
    }
}
