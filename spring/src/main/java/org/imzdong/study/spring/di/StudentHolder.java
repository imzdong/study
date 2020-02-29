package org.imzdong.study.spring.di;

import org.imzdong.study.spring.ioc.di.Student;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/29
 */
public class StudentHolder {

    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "StudentHolder{" +
                "student=" + student +
                '}';
    }
}
