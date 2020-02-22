package org.imzdong.study.spring.bean.model;

import org.imzdong.study.spring.ioc.di.Student;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/22
 */
public interface StudentFactory {

    default Student createStudent(){
        Student student = new Student();
        student.setAge(234);
        student.setSex("StudentFactory");
        return student;
    }
}
