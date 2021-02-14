package org.imzdong.study.spring.proxy.jdk;

import java.lang.reflect.Proxy;

public class ManProxy {

    public static Person getProxy(Person person){
        Class<? extends Person> personClass = person.getClass();
        Object o = Proxy.newProxyInstance(personClass.getClassLoader(),
                personClass.getInterfaces(), (proxy, method, args) -> {
                    System.out.println("执行前...");
                    Object invoke = method.invoke(person, args);
                    System.out.println("执行后...");
                    return invoke;
                });
        return (Person) o;
    }
}
