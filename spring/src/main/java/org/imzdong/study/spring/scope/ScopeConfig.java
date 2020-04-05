package org.imzdong.study.spring.scope;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @description:
 * @author: Winter
 * @time: 2020/4/5
 */
@Configuration
public class ScopeConfig {

    @Bean
    @Scope("singleton")
    public User singletonUser(){
        User user = new User();
        user.setName("singleton:"+System.currentTimeMillis());
        return user;
    }

    @Bean
    @Scope("prototype")
    public User prototypeUser(){
        User user = new User();
        user.setName("prototype:"+System.currentTimeMillis());
        return user;
    }
}
