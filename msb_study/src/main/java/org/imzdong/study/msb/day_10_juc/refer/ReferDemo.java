package org.imzdong.study.msb.day_10_juc.refer;

public class ReferDemo {

    private byte[] bytes;

    public ReferDemo(){
        bytes = new byte[1024*1024*8];
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(Thread.currentThread().getName()+":ReferDemo finalize");
    }
}
