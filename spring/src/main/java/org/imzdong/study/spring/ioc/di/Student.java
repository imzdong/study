package org.imzdong.study.spring.ioc.di;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
public class Student {

    public Student(){
        System.out.println("Student 初始化");
    }

    private int age;
    private String sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    public static Student createStudent(){
        Student student = new Student();
        student.setAge(123);
        student.setSex("create-by-factory");
        return student;
    }
}
