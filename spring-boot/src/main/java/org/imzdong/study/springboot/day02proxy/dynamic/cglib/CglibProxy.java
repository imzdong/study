package org.imzdong.study.springboot.day02proxy.dynamic.cglib;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月28日, 0028 14:17
 */
public class CglibProxy implements MethodInterceptor {

    //利用Enhancer类生成代理类，相当于Proxy.newProxyInstance操作
    public static Object getInstance(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CglibProxy());
        return enhancer.create();
    }

    //Object为由CGLib动态生成的代理类实例
    //Method为被代理的目标方法的引用
    //Object[]为被代理的目标方法的参数列表
    //MethodProxy为对目标方法的代理方法的引用
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before");
        Object obj = methodProxy.invokeSuper(o, objects);
        System.out.println("after");
        return obj;
    }
}
