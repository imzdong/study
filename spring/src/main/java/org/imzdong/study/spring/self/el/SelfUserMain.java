package org.imzdong.study.spring.self.el;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SelfUserMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/self-user-content.xml");
        SelfUser selfUser = context.getBean(SelfUser.class);
        System.out.println(selfUser);
    }
}
