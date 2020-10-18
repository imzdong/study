package org.imzdong.study.msb.design.proxy.dynamic.jdk;

import org.imzdong.study.msb.design.proxy.Man;
import org.imzdong.study.msb.design.proxy.Movable;
import org.imzdong.study.msb.design.proxy.Person;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        Man man = new Man();
        /**
         * 生成代理类方法:类加载器、接口组、代理处理器
         */
        //System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles","true");
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Person proxyPerson = (Person) Proxy.newProxyInstance(Man.class.getClassLoader(),
                new Class[]{Person.class, Movable.class},
                new ManProxyHandler(man));
        proxyPerson.run();
        System.out.println(proxyPerson.age());
        /*Movable movable = (Movable) Proxy.newProxyInstance(Man.class.getClassLoader(),
                new Class[]{Person.class, Movable.class},
                new ManProxyHandler(man));
        movable.fly();*/
    }
}
