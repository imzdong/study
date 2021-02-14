package org.imzdong.study.spring.proxy.cglb;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class WomanCglib implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib 前");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib 后");
        return result;
    }
}
