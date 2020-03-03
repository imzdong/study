package org.imzdong.study.spring.di.model;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * @description:
 * @author: Winter
 * @time: 2020年3月3日, 0003 14:13
 */
public class StudentFactoryBean implements FactoryBean<Student> {
    @Override
    public Student getObject() throws Exception {
        return new Student(3,"我是StudentFactoryBean");
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
