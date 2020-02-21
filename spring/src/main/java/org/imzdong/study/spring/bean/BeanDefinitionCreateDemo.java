package org.imzdong.study.spring.bean;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @description: {@link org.springframework.beans.factory.config.BeanDefinition}
 * @author: Winter
 * @time: 2020/2/21
 */
public class BeanDefinitionCreateDemo {

    public static void main(String[] args) {
        //1、通过BeanDefinitionBuilder构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id",100)
                .addPropertyValue("name","Winter");
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        //beanDefinition非终态，可以继续设置user的属性，相当于Xml的Bean配置
        System.out.println("beanDefinition:" + beanDefinition);
        //beanDefinition:Generic bean: class [org.imzdong.study.spring.ioc.di.User]; scope=;
        // abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true;
        // primary=false;
        // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null

        //2、通过AbstractBeanDefinition直接构建
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("id",100)
                .add("name","Winter");
        genericBeanDefinition.setPropertyValues(propertyValues);
        System.out.println("genericBeanDefinition:" + genericBeanDefinition);
        //genericBeanDefinition:Generic bean: class [org.imzdong.study.spring.ioc.di.User]; scope=;
        // abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true;
        // primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null;
        // destroyMethodName=null
    }
}
