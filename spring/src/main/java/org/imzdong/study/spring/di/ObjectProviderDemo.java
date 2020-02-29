package org.imzdong.study.spring.di;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/29
 */
public class ObjectProviderDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ObjectProviderDemo.class);
        context.refresh();
        /*ObjectProvider<String> beanProvider = context.getBeanProvider(String.class);
        System.out.println(beanProvider.getIfAvailable());*/
        printError(()->context.getBean("user", User.class));
        printError(()->context.getBean(User.class));
        printError(()->context.getBeansOfType(User.class));
        printError(()->context.getBeanProvider(User.class));
    }

    @Bean
    private String initStr(){
        return "Hello,Spring";
    }

    private static void printError(Runnable runnable){
        try {
            runnable.run();
        }catch (BeansException ex){
            ex.printStackTrace();
        }
    }
}
