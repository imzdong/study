package org.imzdong.study.spring.self.supplier;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SupplierMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("self-supplier.xml");
        SupplierUser bean = context.getBean(SupplierUser.class);
        System.out.println(bean);
    }
}
