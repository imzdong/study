package org.imzdong.study.msb.design.proxy.dynamic.spring;

public class ManProxy {

    public void before(){
        System.out.println("spring before");
    }

    public void after(){
        System.out.println("spring after");
    }
}
