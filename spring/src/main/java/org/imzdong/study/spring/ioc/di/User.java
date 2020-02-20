package org.imzdong.study.spring.ioc.di;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
public class User {

    public User(){
        System.out.println("User 初始化");
    }

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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
