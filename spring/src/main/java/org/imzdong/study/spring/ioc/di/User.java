package org.imzdong.study.spring.ioc.di;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.ObjectFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
public class User implements BeanNameAware {

    public User(){
        //System.out.println("User 无参构造 初始化");
    }

    private int id;
    private String name;
    private BeanFactory beanFactory;
    private ObjectFactory<User> objectFactory;
    private String beanName;

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

    @PostConstruct
    public void init() {
        System.out.println("User Bean [" + beanName + "] 初始化...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("User Bean [" + beanName + "] 销毁中...");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
