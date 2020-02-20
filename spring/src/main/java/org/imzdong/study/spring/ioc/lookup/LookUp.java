package org.imzdong.study.spring.ioc.lookup;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
public class LookUp {

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LookUp{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
