package org.imzdong.study.spring.bean;

import org.imzdong.study.spring.bean.model.DefaultStudentFactory;
import org.imzdong.study.spring.bean.model.StudentFactory;
import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/21
 */
public class SignBeanInitializationDemo {

    public static void main(String[] args) {
        // 创建BeanFactory
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //应用启动
        context.refresh();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Student student = new Student();
        student.setSex("DD");
        beanFactory.registerSingleton("signStudent",student);
        System.out.println(context.getBean("signStudent"));
        System.out.println(context.getBean("signStudent") == student);
        context.close();
    }

}
