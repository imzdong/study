package org.imzdong.study.spring.ioc.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * 1、初始化Bean，默认无参构造
 * 2、构造注入：<constructor-arg ref="user"/>
 *    顺序：先初始化依赖类，再初始化自己。
 *    Student 初始化
 *    User 初始化
 *    Constructor初始化 有参
 * 3、属性注入：<property name="user" ref="user"/>
 *    顺序：先初始化自己，再初始化依赖类。
 *    Student 初始化
 *    Constructor初始化
 *    User 初始化
 * @author: Winter
 * @time: 2020/2/20
 */

public class XmlDiDemo {

    public static void main(String[] args) {
        String classPath = "classpath:META-INF/dependency-injection-content.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(classPath);
        /*User user = context.getBean("user", User.class);
        System.out.println(user);*/
        ConstructorEntry constructorEntry = context.getBean("constructorEntry", ConstructorEntry.class);
        System.out.println("constructorEntry:"+constructorEntry);
    }
}
