package org.imzdong.study.spring.ioc.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * 1、注解注入默认无参构造 {@link Component}
 * 3、属性注入：<property name="user" ref="user"/>
 *    顺序：先初始化自己，再初始化依赖类。
 *    Student 初始化
 *    Constructor初始化
 *    User 初始化
 * @author: Winter
 * @time: 2020/2/20
 */
@Component
public class AnnotationDiDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.imzdong.study.spring.ioc.di");
        context.refresh();
        ConstructorEntry constructorEntry = context.getBean("constructorEntry", ConstructorEntry.class);
        System.out.println("constructorEntry:"+constructorEntry);

        User user = context.getBean(User.class);
        System.out.println("user:"+user);
    }

    @Bean
    public User instanceUser(){
        User user = new User();
        user.setId(110);
        user.setName("Winter");
        return user;
    }
}
