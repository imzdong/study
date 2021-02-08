package org.imzdong.study.spring.autowrie;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutowirePersonMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("self-autowire.xml");
        Person personAutowireByName = (Person) context.getBean("personAutowireByName");
        System.out.println(personAutowireByName);
        Person personAutowireByType = (Person) context.getBean("personAutowireByType");
        System.out.println(personAutowireByType);
    }
}
