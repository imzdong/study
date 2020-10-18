package org.imzdong.study.msb.design.proxy.statical;

import org.imzdong.study.msb.design.proxy.Man;

/**
 * 静态代理  语法类似装饰，语义不同。
 * 好多设计模式语法都类似，语义不同。
 * （委托、双亲委派）
 */
public class Main {

    public static void main(String[] args) {
        TimeManProxy man = new TimeManProxy(new LogManProxy(new Man()));
        man.run();
    }
}
