package org.imzdong.study.spring.proxy.jdk;

public class JdkProxyMain {

    public static void main(String[] args) {
        Person person = new Man();
        Person proxy = ManProxy.getProxy(person);
        proxy.buy();
        proxy.eat();
    }
}
