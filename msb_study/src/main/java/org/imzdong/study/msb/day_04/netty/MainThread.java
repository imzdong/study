package org.imzdong.study.msb.day_04.netty;

public class MainThread {

    public static void main(String[] args) {
        //线程组
        NioLoopThreadGroup nioLoopThreadGroup = new NioLoopThreadGroup(3);
        //设置work组，并绑定
        nioLoopThreadGroup.bind(9090);
    }
}
