package org.imzdong.study.spring.bean.model;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/22
 */
public class DefaultStudentFactory implements StudentFactory, InitializingBean, DisposableBean {

    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct init");
    }
    //方法可以私有，可以公有
    private void customInit(){
        System.out.println("自动定义方法初始化 customInit");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化 afterPropertiesSet");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy");
    }

    private void customDestory(){
        System.out.println("customDestory");
    }
}
