package org.imzdong.study.msb.design.proxy.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;
import org.imzdong.study.msb.design.proxy.Man;

/**
 * 底层也是asm
 */
public class Main {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Man.class);
        enhancer.setCallback(new LogInterceptor());
        Man man = (Man) enhancer.create();
        man.fly();
    }
}
