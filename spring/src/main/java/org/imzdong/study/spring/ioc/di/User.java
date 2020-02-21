package org.imzdong.study.spring.ioc.di;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;

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
    private BeanFactory beanFactory;
    private ObjectFactory<User> objectFactory;

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

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<User> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<User> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", beanFactory=" + beanFactory +
                '}';
    }
}
