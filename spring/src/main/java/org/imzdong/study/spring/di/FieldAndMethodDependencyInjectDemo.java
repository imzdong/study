package org.imzdong.study.spring.di;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @description:
 * 1、属性注入
 *    1.1、Xml注入
 *    1.2、注解注入
 *    1.3、API注入
 * 2、方法注入
 *    2.1、ByType
 *    2.2、ByName
 * @author: Winter
 * @time: 2020/2/29
 */
public class FieldAndMethodDependencyInjectDemo implements BeanFactoryAware, ApplicationContextAware {

    @Autowired //通过字段
    private Student studentAutowired;
    @Resource //通过字段
    private Student studentResource;
    private static Student studentAutowiredMethod;
    private static Student studentResourceMethod;

    //通过aware接口回调注入类
    private static BeanFactory beanFactoryStatic;
    private static ApplicationContext applicationContextStatic;

    @Autowired //通过方法
    private void autoStudent(Student student){
        studentAutowiredMethod = student;
    }
    @Resource //通过方法
    private void resourceStudent(Student student){
        studentResourceMethod = student;
    }

    @Bean
    private Student initStudent(){
        Student student = new Student();
        student.setSex("字段和方法注入");
        student.setAge(666);
        return student;
    }

    public static void main(String[] args) {
        FieldAndMethodDependencyInjectDemo demo = new FieldAndMethodDependencyInjectDemo();
        printStr(demo);
        System.out.println("=====================开始启动BeanFactory分割线====================");
        annotationInject();
    }




    private static void annotationInject() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(FieldAndMethodDependencyInjectDemo.class);
        context.refresh();
        FieldAndMethodDependencyInjectDemo demo = context.getBean(FieldAndMethodDependencyInjectDemo.class);
        printStr(demo);
        System.out.println("beanFactoryStatic == beanFactory："+(beanFactoryStatic == context.getBeanFactory()));
        System.out.println("applicationContextStatic == context："+ (applicationContextStatic==context));
        context.close();
        /**
         * 执行结果
         * studentAutowired：null
         * studentResource：null
         * studentAutowiredMethod：null
         * studentResourceMethod：null
         * beanFactoryStatic：null
         * applicationContextStatic：null
         * =====================开始启动BeanFactory分割线====================
         * Student 初始化
         * studentAutowired：Student{age=666, sex='字段和方法注入'}
         * studentResource：Student{age=666, sex='字段和方法注入'}
         * studentAutowiredMethod：Student{age=666, sex='字段和方法注入'}
         * studentResourceMethod：Student{age=666, sex='字段和方法注入'}
         * beanFactoryStatic：org.springframework.beans.factory.support.DefaultListableBeanFactory@d2cc05a: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,fieldAndMethodDependencyInjectDemo,initStudent]; root of factory hierarchy
         * applicationContextStatic：org.springframework.context.annotation.AnnotationConfigApplicationContext@6576fe71, started on Sat Feb 29 21:35:08 CST 2020
         * beanFactoryStatic == beanFactory：true
         * applicationContextStatic == context：true
         */
    }

    private static void printStr(FieldAndMethodDependencyInjectDemo demo){
        System.out.println("studentAutowired："+demo.studentAutowired);
        System.out.println("studentResource："+demo.studentResource);
        System.out.println("studentAutowiredMethod："+studentAutowiredMethod);
        System.out.println("studentResourceMethod："+studentResourceMethod);
        System.out.println("beanFactoryStatic："+beanFactoryStatic);
        System.out.println("applicationContextStatic："+applicationContextStatic);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        beanFactoryStatic = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContextStatic = applicationContext;
    }
}
