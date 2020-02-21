package org.imzdong.study.spring.bean;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @description:
 * @author: Winter
 * @time: 2020/2/21
 */
@Import(AnnotationBeanDefinitionDemo.Student.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        // 创建BeanFactory
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationBeanDefinitionDemo.class);
        resisterBeanDefintion(context,"Good");
        resisterBeanDefintion(context,null);
        //应用启动
        context.refresh();
        Map<String, Student> studentMap = context.getBeansOfType(Student.class);
        System.out.println("studentMap："+studentMap);
        Map<String, User> userMap = context.getBeansOfType(User.class);
        System.out.println("userMap："+userMap);
        //userMap：{Good=User{id=100, name='Winter', beanFactory=null},
        // org.imzdong.study.spring.ioc.di.User#0=User{id=100, name='Winter', beanFactory=null}}
        context.close();
    }

    private static void resisterBeanDefintion(BeanDefinitionRegistry registry,
                                              String beanName){
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(User.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("id",100)
                .add("name","Winter");
        genericBeanDefinition.setPropertyValues(propertyValues);
        if(StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, genericBeanDefinition);
        }else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(genericBeanDefinition,registry);
        }
    }

    @Bean(name = {"student","Winter"})//通过Bean注入 2、通过Component 3、通过Import
    public Student instanceStudent(){
        Student student = new Student();
        student.setAge(123);
        student.setSex("Man");
        return student;
    }

    @Component
    static class Student{
        private int age;
        private String sex;

        public Student(){
            System.out.println("Student无参初始化");
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }
}
