package org.imzdong.study.spring.bean;

import org.imzdong.study.spring.bean.model.DefaultStudentFactory;
import org.imzdong.study.spring.bean.model.StudentFactory;
import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.ServiceLoader;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/22
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/special-bean-instantiation-content.xml");
        context.refresh();
        ServiceLoader<StudentFactory> studentMap = context.getBean(ServiceLoader.class);
        /**
         * 1、在META-INF/services下设置org.imzdong.study.spring.bean.model.StudentFactory
         * 2、在xml配置
         *     <bean id="studentFactoryBean" class="org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean">
         *         <property name="serviceType" value="org.imzdong.study.spring.bean.model.StudentFactory"/>
         *     </bean>
         * 3、获取serviceLoader
         */
        studentMap.iterator().forEachRemaining(
                studentFactory -> System.out.println(studentFactory.createStudent())
        );
        serviceLoader();

        //通过AutowireCapableBeanFactory 创建
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        DefaultStudentFactory studentFactory = beanFactory.createBean(DefaultStudentFactory.class);
        System.out.println("studentFactory:"+studentFactory.createStudent());
    }

    public static void serviceLoader(){
        ServiceLoader<StudentFactory> load = ServiceLoader.load(StudentFactory.class, SpecialBeanInstantiationDemo.class.getClassLoader());
        load.iterator().forEachRemaining(student->System.out.println(student.createStudent()));
        //Student{age=234, sex='StudentFactory'}
    }
}
