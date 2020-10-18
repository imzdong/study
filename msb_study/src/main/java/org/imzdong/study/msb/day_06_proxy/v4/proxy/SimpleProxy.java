package org.imzdong.study.msb.day_06_proxy.v4.proxy;

import java.lang.reflect.Proxy;

public class SimpleProxy {

    public static <T>T getProxy(Class<T> interfaceClass){
        ClassLoader loader = interfaceClass.getClassLoader();
        Class<?> [] interfaces = {interfaceClass};
        return (T) Proxy.newProxyInstance(loader, interfaces, new DynamicProxy(interfaceClass));
    }

}
