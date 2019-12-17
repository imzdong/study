package org.imzdong.study.springboot.day02proxy.dynamic.jdk;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月27日, 0027 19:42
 */
public class DynamicTest {
    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy(new RealPeople());
        PeopleInterface peopleInterface = (PeopleInterface) Proxy.newProxyInstance(PeopleInterface.class.getClassLoader(), new Class[]{
                PeopleInterface.class
        }, dynamicProxy);
        peopleInterface.buy();
    }
}
