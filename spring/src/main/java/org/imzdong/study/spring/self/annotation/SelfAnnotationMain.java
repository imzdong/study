package org.imzdong.study.spring.self.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SelfAnnotationMain {

    /**
     * 先实例化及初始化所有的Bean
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("self-annotation.xml");
        Person person = context.getBean(Person.class);
        System.out.println(person);
        /*String path ="org/imzdong/study/spring/self/annotation";
        ApplicationContext context = new AnnotationConfigApplicationContext(path);*/

        GoodMan goodMan = context.getBean(GoodMan.class);
        System.out.println(goodMan);
    }
}
