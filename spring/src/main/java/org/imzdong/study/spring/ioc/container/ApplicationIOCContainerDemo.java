package org.imzdong.study.spring.ioc.container;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/21
 */
@Configuration
public class ApplicationIOCContainerDemo {

    public static void main(String[] args) {
        // 创建BeanFactory
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //context.scan("org.imzdong.study.spring.ioc.container");
        context.register(ApplicationIOCContainerDemo.class);
        //应用启动
        context.refresh();
        User user = context.getBean(User.class);
        System.out.println("装载user："+user);
        System.out.println("装载user.getObjectFactory():："+user.getObjectFactory());
        context.close();
    }

    @Bean
    public User instanceUser(){
        User user = new User();
        user.setName("Winter");
        user.setId(12323);
        return user;
    }
}
