package org.imzdong.study.spring.di.model;


import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

/**
 * @description:
 * @author: Winter
 * @time: 2020年3月3日, 0003 14:16
 */
public class StudentObjectFactory2 implements ObjectFactory<Student> {

    @Override
    public Student getObject() throws BeansException {
        return new Student(5,"StudentObjectFactory");
    }
}
