package org.imzdong.study.spring.ioc.beans;

/**
 *
 * @description: 实例类
 * setter/getter 方法
 * @author: Winter
 * @since: 0.0.1(Major.Minor.Patch)
 */
public class Person {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Persion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
