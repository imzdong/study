package org.imzdong.study.spring.proxy.cglb;

import org.springframework.cglib.proxy.Enhancer;

public class CglibProxyMan {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Woman.class);
        enhancer.setCallback(new WomanCglib());
        Woman woman = (Woman) enhancer.create();
        woman.buy();
        woman.eat();
    }
}
