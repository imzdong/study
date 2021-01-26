package org.imzdong.study.spring.self.editor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SelfCustomerMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("" +
                "self-editor.xml");
        SelfCustomer selfCustomer = context.getBean(SelfCustomer.class);
        System.out.println(selfCustomer);
    }
}
