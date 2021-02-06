package org.imzdong.study.spring.self.lookUp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LookUpMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("methodOverride.xml");
        //增强类Apple
        Object fruitPlateApple = context.getBean("fruitPlateApple");
        System.out.println(fruitPlateApple);
        //增强类banana
        Object fruitPlateBanana = context.getBean("fruitPlateBanana");
        System.out.println(fruitPlateBanana);
    }
}
