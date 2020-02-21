package org.imzdong.study.spring.ioc.di;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

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
        User user = context.getBean("user", User.class);
        //依赖注入
        System.out.println(user);
        System.out.println(user.getObjectFactory().getObject());
        //User{id=666, name='Winter', beanFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory@2133814f:
        // defining beans [student,constructorEntry,user]; root of factory hierarchy}
        //依赖查找
        //BeanFactory beanFactory = context.getBean(BeanFactory.class);
        //System.out.println(beanFactory);//异常获取不到
        /*ConstructorEntry constructorEntry = context.getBean("constructorEntry", ConstructorEntry.class);
        System.out.println("constructorEntry:"+constructorEntry);*/
        Environment environment = context.getBean(Environment.class);
        System.out.println("容器内建Bean：environment" + environment);
        /**
         * 1、自定义Bean
         *    User
         * 2、容器内建Bean对象（可以查到）
         *    Environment
         * 3、容器内建依赖(通过getBean获取不到)
         *    BeanFactory
         */
    }
}
