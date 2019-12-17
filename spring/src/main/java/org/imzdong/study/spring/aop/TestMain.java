package org.imzdong.study.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: Winter
 * @time: 2019年12月17日, 0017 14:35
 */
public class TestMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AspectConfig.class);
        TestService testService = ctx.getBean(TestService.class);
        String winter = testService.sayHello("Winter");
        System.out.println(winter);
    }

}
