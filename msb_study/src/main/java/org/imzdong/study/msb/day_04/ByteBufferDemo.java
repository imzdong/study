package org.imzdong.study.msb.day_04;

import java.nio.ByteBuffer;

public class ByteBufferDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(9);
        //buffer:java.nio.HeapByteBuffer[pos=0 lim=1024 cap=1024]
        System.out.println("buffer:"+buffer);
        buffer.put("Winter".getBytes());
        System.out.println("buffer:"+buffer);
        /*System.out.println("buffer.get():"+new String(new byte[]{buffer.get()}));
        System.out.println("buffer:"+buffer);*/
        System.out.println("buffer.get(0):"+new String(new byte[]{buffer.get(0)}));
        System.out.println("buffer:"+buffer);
        /*System.out.println("buffer.put(10):"+buffer.putInt(1));//4个字节
        System.out.println("buffer:"+buffer);*/
        //flip(buffer);
        //剩余
        System.out.println("buffer.hasRemaining():"+buffer.hasRemaining());
        buffer.clear();
        //buffer.clear(): java.nio.HeapByteBuffer[pos=0 lim=9 cap=9]
        System.out.println("buffer.clear(): " + buffer);
        System.out.println("buffer.hasRemaining():"+buffer.hasRemaining());
        //压缩
        buffer.compact();
        //buffer.compact():java.nio.HeapByteBuffer[pos=3 lim=9 cap=9]
        System.out.println("buffer.compact():"+buffer);
    }

    private static void flip(ByteBuffer buffer) {
        buffer.flip();
        //flip倒置重新添加覆盖原值 buffer大小由limit决定
        //buffer.flip():java.nio.HeapByteBuffer[pos=0 lim=6 cap=9]
        System.out.println("buffer.flip():"+buffer);
        printBuffer(buffer);
        byte b = 12;
        buffer.put(7,b);
        System.out.println("buffer.put(7,b);:"+buffer);
        printBuffer(buffer);
    }

    private static  void printBuffer(ByteBuffer buffer){
        int limit = buffer.limit();
        int position = buffer.position();
        for(int num=0;num<limit;num++){
            System.out.println("buffer.get("+num+"):"+new String(new byte[]{buffer.get(num)}));
            System.out.println("buffer:"+buffer);
            System.out.println("-----------------");
        }
    }
}
