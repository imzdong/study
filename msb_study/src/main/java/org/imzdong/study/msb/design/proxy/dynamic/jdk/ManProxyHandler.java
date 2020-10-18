package org.imzdong.study.msb.design.proxy.dynamic.jdk;

import org.imzdong.study.msb.design.proxy.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Man的动态代理处理类
 */
public class ManProxyHandler implements InvocationHandler {

    private Person person;

    public ManProxyHandler(Person person) {
        this.person = person;
    }

    /**
     *
     * @param proxy 生成的动态代理类
     * @param method 代理的方法
     * @param args 代理方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("dynamic start");
        Object invoke = method.invoke(person, args);
        System.out.println("dynamic end");
        return invoke;
    }
}
