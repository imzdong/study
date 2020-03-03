package org.imzdong.study.spring.di;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @description:
 * @author: Winter
 * @time: 2020年3月3日, 0003 13:27
 */
@Component
public class QualifierInjectDemo {

    @Autowired
    private Collection<Student> students;

    @Autowired
    @Qualifier("分组标识") //Qualifier标识的是一组
    private Collection<Student> studentsQualifier;

    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("/org/imzdong/study/spring/di/");
        context.refresh();
        Map<String, Student> studentMap = context.getBeansOfType(Student.class);
        System.out.println("studentMap:"+studentMap);
        //context.getBean(QualifierInjectDemo.class) 不安全
        //No qualifying bean of type 'org.imzdong.study.spring.di.QualifierInjectDemo' available
        //QualifierInjectDemo qualifierInjectDemo = context.getBean(QualifierInjectDemo.class);
        //testFactory(context);
        ObjectProvider<QualifierInjectDemo> beanProvider = context.getBeanProvider(QualifierInjectDemo.class);
        System.out.println("beanProvider:"+beanProvider);
        //beanProvider.getObject() No qualifying bean of type 'org.imzdong.study.spring.di.QualifierInjectDemo' available
        QualifierInjectDemo qualifierInjectDemo = beanProvider.getIfAvailable();
        System.out.println("qualifierInjectDemo:"+qualifierInjectDemo);
        System.out.println("students:"+qualifierInjectDemo.students);
        System.out.println("studentsQualifier:"+qualifierInjectDemo.studentsQualifier);
        context.close();

    }

    private static void testFactory(AnnotationConfigApplicationContext context) throws Exception {
        ObjectFactory objectFactory = context.getBean(ObjectFactory.class);
        System.out.println("objectFactory:"+objectFactory);
        System.out.println("objectFactory.getObject:"+objectFactory.getObject());
        FactoryBean factoryBean = context.getBean(FactoryBean.class);
        System.out.println("factoryBean:"+factoryBean);
        System.out.println("factoryBean.getObject:"+factoryBean.getObject());
    }
}
