package org.imzdong.study.msb.day_04_jdk_nio;

import java.nio.ByteBuffer;

/**
 * 读写特殊
 * 写：limit和 cap 一致 pos移动
 * flip
 * 读：limit有效数的位置，cap容量，pos
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        simpleReadAndWrite();
        readBytes();
    }

    /**
     * 读取数组
     */
    private static void readBytes() {
        ByteBuffer buffer = ByteBuffer.wrap("Winter".getBytes());
        byte[] dist =new byte[buffer.remaining()];
        buffer.get(dist);
        System.out.println(new String(dist));
    }

    /**
     buffer:java.nio.HeapByteBuffer[pos=0 lim=9 cap=9]
     buffer:java.nio.HeapByteBuffer[pos=6 lim=9 cap=9]
     buffer:java.nio.HeapByteBuffer[pos=0 lim=6 cap=9]
     buffer.get():W
     buffer.get():i
     buffer.get():n
     buffer.get():t
     buffer.get():e
     buffer.get():r
     buffer:java.nio.HeapByteBuffer[pos=6 lim=6 cap=9]
     */
    private static void simpleReadAndWrite() {
        ByteBuffer buffer = ByteBuffer.allocate(9);
        System.out.println("buffer:"+buffer);
        buffer.put("Winter".getBytes());
        System.out.println("buffer:"+buffer);
        buffer.flip();//写读切换
        System.out.println("buffer:"+buffer);
        while (buffer.hasRemaining()) {
            System.out.println("buffer.get():" + new String(new byte[]{buffer.get()}));
        }
        System.out.println("buffer:"+buffer);
    }
}
