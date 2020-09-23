package org.imzdong.study.netty.spring.mybatis.model;

/**
 * @description:
 * @author: Winter
 * @time: 2020年8月31日, 0031 21:35
 */
public class User {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
