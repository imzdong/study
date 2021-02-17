package org.imzdong.study.spring.self.factoryBean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SelfFactoryBeanMain {

    public static void main(String[] args) {
        String path = "org/imzdong/study/spring/self/factoryBean";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(path);
        Object selfFactoryBean = context.getBean("selfFactoryBean");
        System.out.println(selfFactoryBean);
    }
}
