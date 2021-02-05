package org.imzdong.study.spring.self.lookUp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LookUpMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("methodOverride.xml");
        Object fruitPlateApple = context.getBean("fruitPlateApple");
        System.out.println(fruitPlateApple);
        Object fruitPlateBanana = context.getBean("fruitPlateBanana");
        System.out.println(fruitPlateBanana);
    }
}
