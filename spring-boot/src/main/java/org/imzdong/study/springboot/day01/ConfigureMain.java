package org.imzdong.study.springboot.day01;

import org.imzdong.study.springboot.day02enable.EnableConfigure;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月12日, 0012 13:48
 */
public class ConfigureMain {

    public static void main(String[] args) {
        //1、AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ConfigurationDemo.class);
        //2.1、String basePackages = "org.imzdong.springboot.day01";
        //2.2、AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(basePackages);
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(EnableConfigure.class);
        String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);
    }
}
