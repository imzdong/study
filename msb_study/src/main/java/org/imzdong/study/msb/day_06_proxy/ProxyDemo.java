package org.imzdong.study.msb.day_06_proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @description:
 * @author: Winter
 * @time: 2020年9月25日, 0025 17:15
 */
public class ProxyDemo {

    public static void main(String[] args) {
        server();
        client();
    }

    private static void server() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup work = boss;
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture bind = bootstrap.group(boss, work)
                .channel(ServerSocketChannel.class)
                .childHandler(null)
                .bind(new InetSocketAddress(9090));
        try {
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void client() {
        ClassLoader loader = ProxyDemo.class.getClassLoader();
        Class<?> [] interfaces = {Animal.class};
        Animal animal = (Animal) Proxy.newProxyInstance(loader, interfaces, new DynamicProxy<Animal>());
        animal.eat("肉");
    }
}

class DynamicProxy<T> implements InvocationHandler{
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        T realObject = (T) proxy;
        String name = realObject.getClass().getName();
        String methodName = method.getName();
        System.out.println(String.format("real class name：%s，method：%s，parmas：%s", name , methodName, args[0]));
        return 1;
    }
}
interface Animal{

     int eat(String food);
}
