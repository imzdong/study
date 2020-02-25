package org.imzdong.study.spring.di;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/25
 */
public class HierarchicalBeanFactoryDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableListableBeanFactory localBeanFactory = context.getBeanFactory();
        BeanFactory parentBeanFactory = localBeanFactory.getParentBeanFactory();
        System.out.println("set before parentBeanFactory: "+parentBeanFactory);
        localBeanFactory.setParentBeanFactory(creatBeanFactory());
        parentBeanFactory = localBeanFactory.getParentBeanFactory();
        System.out.println("set after parentBeanFactory: "+parentBeanFactory);
        System.out.println("LocalBeanFactory containsBean Winter0225: "+localBeanFactory.containsLocalBean("Winter0225"));
        System.out.println("ParentBeanFactory containsBean Winter0225: "+parentBeanFactory.containsBean("Winter0225"));

        System.out.println("LocalBeanFactory get Winter0225: "+localBeanFactory.getBean("Winter0225"));
        System.out.println("ParentBeanFactory get Winter0225: "+parentBeanFactory.getBean("Winter0225"));
        /**
         * set before parentBeanFactory: null
         * set after parentBeanFactory: org.springframework.beans.factory.support.DefaultListableBeanFactory@192d3247: defining beans [Winter0225]; root of factory hierarchy
         * LocalBeanFactory containsBean Winter0225: false
         * ParentBeanFactory containsBean Winter0225: true
         * User 初始化
         * LocalBeanFactory get Winter0225: User{id=678, name='Winter Good', beanFactory=null}
         * ParentBeanFactory get Winter0225: User{id=678, name='Winter Good', beanFactory=null}
         */
    }


    private static BeanFactory creatBeanFactory(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id",678).addPropertyValue("name","Winter Good");
        beanFactory.registerBeanDefinition("Winter0225",beanDefinitionBuilder.getBeanDefinition());
        return beanFactory;
    }
}
