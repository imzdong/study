package org.imzdong.study.spring.self.factory;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SelfBeanDRPPMain {

    public static void main(String[] args) {
        String path = "META-INF/self-bdrpp-content.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(path);
    }
}
