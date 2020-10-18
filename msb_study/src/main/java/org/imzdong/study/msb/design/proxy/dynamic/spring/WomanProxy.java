package org.imzdong.study.msb.design.proxy.dynamic.spring;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class WomanProxy {

    @Before("execution (void org.imzdong.study.msb.design.proxy.Woman.buy())")
    public void before() {
        System.out.println("method start.." + System.currentTimeMillis());
    }

    @After("execution (void org.imzdong.study.msb.design.proxy.Woman.buy())")
    public void after() {
        System.out.println("method stop.." + System.currentTimeMillis());
    }
}
