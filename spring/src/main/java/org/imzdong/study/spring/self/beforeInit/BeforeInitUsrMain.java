package org.imzdong.study.spring.self.beforeInit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeforeInitUsrMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("self-before-init.xml");
        BeforeInitUser bean = context.getBean(BeforeInitUser.class);
        bean.doSomething();
    }
}
