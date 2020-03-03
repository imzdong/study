package org.imzdong.study.spring.di.model;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Winter
 * @time: 2020年3月3日, 0003 13:28
 */
@Configuration
public class StudentConfigrue {

    @Bean
    public Student student1(){
        return new Student(1,"我是01");
    }

    @Bean
    @Qualifier("分组标识-1")
    public Student student2(){
        return new Student(2,"我是02");
    }

    @Bean
    public StudentFactoryBean student3(){
        return new StudentFactoryBean();
    }

    @Bean
    @Qualifier("分组标识")
    public StudentFactoryBean2 student6(){
        return new StudentFactoryBean2();
    }

    @Bean
    public StudentObjectFactory student4(){
        return new StudentObjectFactory();
    }

    @Bean
    public StudentObjectFactory2 student5(){
        return new StudentObjectFactory2();
    }
}
