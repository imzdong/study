package org.imzdong.study.spring.ioc.lookup;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;


/**
 * @description:
 * @author: Winter
 * @time: 2020/2/20
 */
public class LookUpDemo {

    public static void main(String[] args) {
        /**
         * spring/src/main/resources/META-INF/lookup-content.xml
         * D:\WorkSpace\Java\study\spring\src\main\resources\META-INF\lookup-content.xml
         */
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/lookup-content.xml");
        context.refresh();
        /*LookUp lookUp = context.getBean(LookUp.class);
        System.out.println("实时加载LookUp:"+lookUp);*/
        //实时加载LookUp:LookUp{age=18, name='Winter'}
        //延迟加载
        ObjectFactory<LookUp> lookUpLazy = context.getBean(ObjectFactory.class);
        System.out.println("实时加载LookUp:"+lookUpLazy.getObject());
        //实时加载LookUp:LookUp{age=18, name='Winter'}
        //批量查找
        Map<String, LookUp> lookUps = context.getBeansOfType(LookUp.class);
        System.out.println("批量加载LookUps:"+lookUps);
        //批量加载LookUps:{lookUp=LookUp{age=18, name='Winter'}}
        //批量加载LookUps:{lookUp=LookUp{age=18, name='Winter'}, superLookUp=SuperLookUp{address='北京'}}
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(Super.class);
        System.out.println("beansWithAnnotation:"+beansWithAnnotation);
        //beansWithAnnotation:{superAnnotation=SuperAnnotation{id=123}}
    }
}
