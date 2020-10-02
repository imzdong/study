package org.imzdong.study.msb.design.proxy.dynamic.spring;

import org.imzdong.study.msb.design.proxy.Woman;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainAuto {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/config_auto.xml");
        Woman woman = context.getBean(Woman.class);
        woman.buy();
    }
}
