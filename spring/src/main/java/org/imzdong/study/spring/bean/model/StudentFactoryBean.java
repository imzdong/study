package org.imzdong.study.spring.bean.model;

import org.imzdong.study.spring.ioc.di.Student;
import org.springframework.beans.factory.FactoryBean;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/22
 */
public class StudentFactoryBean implements FactoryBean<Student> {
    @Override
    public Student getObject() throws Exception {
        Student student = new Student();
        student.setSex("StudentFactoryBean");
        student.setAge(456);
        return student;
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }
}
