package org.imzdong.study.msb.day_04.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class NioLoopThreadGroup {

    NioLoopThread [] nioLoopThreads;
    ServerSocketChannel ssc;
    AtomicInteger atomicInteger = new AtomicInteger();

    public NioLoopThreadGroup(int num){
        nioLoopThreads = new NioLoopThread [num];
        for (int i=0;i<num;i++){
            nioLoopThreads[i] = new NioLoopThread();
            new Thread(nioLoopThreads[i]).start();
        }
    }

    public void bind(int port) {
        try {
            NioLoopThread nlt = nextNlt();
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            nlt.lbq.put(ssc);
            nlt.selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public NioLoopThread nextNlt(){
        int index = atomicInteger.incrementAndGet() % nioLoopThreads.length;
        return nioLoopThreads[index];
    }
}
