package org.imzdong.study.spring.di;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @description:
 * 1、手动注入
 *    1.1、Xml注入
 *    1.2、注解注入
 *    1.3、API注入
 * 2、自动注入
 *    2.1、ByType
 *    2.2、ByName
 * @author: Winter
 * @time: 2020/2/29
 */
public class DependencyInjectSetterDemo {

    public static void main(String[] args) {
        //1、手动注入
        //1.1、Xml注入
        //META-INF/dependency-inject-setter.xml
        xmlInjectSetter();
        //1.2、注解注入
        annotationInject();
        //1.3、API注入
        apiInject();
        //2、自动注入
        //2.1、ByName  xml添加 autowire="byType"
        //2.2、ByType autowire="byName"
    }

    private static void apiInject() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionBuilder studentHolderBuilder = BeanDefinitionBuilder.genericBeanDefinition(StudentHolder.class);
        studentHolderBuilder.addPropertyReference("student","studentBean");
        beanFactory.registerBeanDefinition("studentHolder",studentHolderBuilder.getBeanDefinition());
        BeanDefinitionBuilder studentBuilder = BeanDefinitionBuilder.genericBeanDefinition(Student.class);
        studentBuilder.addPropertyValue("sex","api依赖").addPropertyValue("age",123);
        beanFactory.registerBeanDefinition("studentBean",studentBuilder.getBeanDefinition());
        System.out.println("============================");
        StudentHolder studentHolder = beanFactory.getBean(StudentHolder.class);
        System.out.println("StudentHolder: "+studentHolder);
    }

    private static void annotationInject() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DependencyInjectSetterDemo.class);
        XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(context);
        definitionReader.loadBeanDefinitions("classpath:META-INF/dependency-inject-setter.xml");
        context.refresh();
        StudentHolder studentHolder = context.getBean(StudentHolder.class);
        System.out.println("StudentHolder: "+studentHolder);
        context.close();
    }

    @Bean
    @Primary
    private StudentHolder initStudentHolder(Student student){
        StudentHolder studentHolder = new StudentHolder();
        studentHolder.setStudent(student);
        return studentHolder;
    }

    private static void xmlInjectSetter() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);
        definitionReader.loadBeanDefinitions("classpath:META-INF/dependency-inject-setter.xml");
        StudentHolder studentHolder = beanFactory.getBean(StudentHolder.class);
        System.out.println("StudentHolder: "+studentHolder);
    }
}
