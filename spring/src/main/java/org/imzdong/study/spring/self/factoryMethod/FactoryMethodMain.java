package org.imzdong.study.spring.self.factoryMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FactoryMethodMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("self-factory-method.xml");
        FactoryUser bean = (FactoryUser) context.getBean("factoryUser");
        System.out.println(bean);

        FactoryUser bean2 = (FactoryUser) context.getBean("factoryUser2");
        System.out.println(bean2);
    }
}
