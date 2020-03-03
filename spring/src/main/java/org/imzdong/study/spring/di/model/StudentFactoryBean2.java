package org.imzdong.study.spring.di.model;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * @description:
 * @author: Winter
 * @time: 2020年3月3日, 0003 14:13
 */
public class StudentFactoryBean2 implements FactoryBean<Student> {
    @Override
    public Student getObject() throws Exception {
        return new Student(6,"我是StudentFactoryBean");
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
