package org.imzdong.study.spring.ioc.di;

import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
@Component
public class ConstructorEntry {

    public ConstructorEntry(){
        System.out.println("Constructor初始化");
    }
    //@Autowired  先初始化User
    public ConstructorEntry(User user){
        System.out.println("Constructor初始化 有参");
        this.user = user;
    }
    //@Autowired 谁在前谁先
    private User user;

    public User getUser() {
        return user;
    }
    @Autowired
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ConstructorEntry{" +
                "user=" + user +
                '}';
    }
}
