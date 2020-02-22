package org.imzdong.study.spring.bean;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/22
 */
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/bean-instantiation-content.xml");
        context.refresh();
        Map<String, Student> studentMap = context.getBeansOfType(Student.class);
        System.out.println("studentMap："+studentMap);
        //studentMap：{student=Student{age=0, sex='null'},
        // student-by-static-method=Student{age=123, sex='create-by-factory'},
        // student-by-instance-method=Student{age=234, sex='StudentFactory'},
        // student-by-factoryBean=Student{age=456, sex='StudentFactoryBean'}}
    }
}
