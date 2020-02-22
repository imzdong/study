package org.imzdong.study.spring.bean;

import org.imzdong.study.spring.bean.model.DefaultStudentFactory;
import org.imzdong.study.spring.bean.model.StudentFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/21
 */
@Configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        // 创建BeanFactory
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanInitializationDemo.class);
        //应用启动
        context.refresh();
        context.close();
        /**
         * @PostConstruct init
         * 初始化 afterPropertiesSet
         * 自动定义方法初始化 customInit
         * @PreDestroy preDestroy
         * DisposableBean destroy
         * customDestory
         */
    }


    @Bean(initMethod="customInit",destroyMethod = "customDestory")
    //@Lazy //延迟加载，使用的时候才初始化，非延迟加载，容器初启动完就加载
    public StudentFactory instanceStudent(){
        StudentFactory student = new DefaultStudentFactory();
        return student;
    }

}
