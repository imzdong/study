package org.imzdong.study.performance.jdk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Winter
 * @time: 2020/3/2
 */
public class Java8Collection {

    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Winter1",1,"desc1"));
        list.add(new Student("Winter2",2,"desc2"));
        list.add(new Student("Winter3",3,"desc3"));
        list.add(new Student("Winter4",4,"desc4"));
        list.add(new Student("Winter5",5,"desc5"));
        list.add(new Student("Winter6",6,"desc6"));
        List<Student> student5 = list.stream().filter(student -> student.id >= 5)
                .map(student -> {
                    student.setName(student.getName()+"5add");
                    return student;
                }).collect(Collectors.toList());
        List<Student> student3 = list.stream().filter(student -> student.id >= 3)
                .map(student -> {
                    student.setDesc(student.getDesc()+"3add");
                    return student;
                }).collect(Collectors.toList());
        System.out.println(student5);
        System.out.println(student3);
        System.out.println(list);
        list.removeAll(student5);
        System.out.println(list);
        student3.addAll(student5);
        System.out.println(student3);
    }

    static class Student{

        public Student(String name, int id, String desc) {
            this.name = name;
            this.id = id;
            this.desc = desc;
        }

        private String name;
        private int id;
        private String desc;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }
}
