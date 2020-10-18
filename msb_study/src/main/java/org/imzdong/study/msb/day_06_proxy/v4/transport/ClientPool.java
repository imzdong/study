package org.imzdong.study.msb.day_06_proxy.v4.transport;

import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientPool{
    int poolSize;
    NioSocketChannel[] nscs;
    Object[] locks;
    ClientPool(int poolSize){
        this.poolSize = poolSize;
        nscs = new NioSocketChannel[poolSize];
        locks = new Object[poolSize];
        for (int i = 0; i < poolSize; i++) {
            locks[i] = new Object();
        }
    }
}
