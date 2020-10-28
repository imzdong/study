package org.imzdong.study.spring.ioc.container;

import org.imzdong.study.spring.ioc.beans.Person;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @description:
 * @author: Winter
 * @time: 2020年10月22日, 0022 18:30
 */
public class BeanFactoryDemo {

    public static void main(String[] args) {
        ClassPathResource resource = new ClassPathResource("META-INF/spring-config.xml");
        XmlBeanFactory factory = new XmlBeanFactory(resource);
        Person person = (Person) factory.getBean("person");
        System.out.println(person);
    }
}
