package org.imzdong.study.spring.ioc.container;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/21
 */
public class BeanFactoryIOCContainerDemo {

    public static void main(String[] args) {
        // 创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);
        int definitions = definitionReader.loadBeanDefinitions("classpath:META-INF/dependency-injection-content.xml");
        System.out.println("装载Bean的数量："+definitions);
        User user = beanFactory.getBean(User.class);
        System.out.println("装载user："+user);
        System.out.println("装载user.getObjectFactory():："+user.getObjectFactory());
    }
}
