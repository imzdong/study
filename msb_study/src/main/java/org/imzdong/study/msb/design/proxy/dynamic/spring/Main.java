package org.imzdong.study.msb.design.proxy.dynamic.spring;

import org.imzdong.study.msb.design.proxy.Man;
import org.imzdong.study.msb.design.proxy.Movable;
import org.imzdong.study.msb.design.proxy.Woman;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/config.xml");
        //Man bean = context.getBean(Man.class);
        Movable bean = (Movable) context.getBean("man");
        bean.fly();
        Woman woman = context.getBean(Woman.class);
        woman.buy();
    }
}
