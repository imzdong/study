package org.imzdong.study.spring.bean;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/21
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        //META-INF/bean-content.xml
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/bean-content.xml");
        context.refresh();
        User winter = context.getBean("Winter", User.class);
        User user = context.getBean("user", User.class);
        System.out.println("winter == user " + (user == winter));
        //winter == user true
    }
}
