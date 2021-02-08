package org.imzdong.study.spring.self.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SelfAnnotationMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("self-annotation.xml");
        Person person = context.getBean(Person.class);
        System.out.println(person);
    }
}
