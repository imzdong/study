package org.imzdong.study.netty.spring;

import org.imzdong.study.netty.spring.bean.HelloWord;
import org.imzdong.study.netty.spring.mybatis.UserMapper;
import org.imzdong.study.netty.spring.mybatis.model.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: Winter
 * @time: 2020年8月31日, 0031 21:10
 */
public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        /*SqlSessionTemplate bean = context.getBean(SqlSessionTemplate.class);
        UserMapper mapper = bean.getMapper(UserMapper.class);
        User user = mapper.getUser("1");
        System.out.println(user);*/
        UserMapper bean = context.getBean(UserMapper.class);
        User user = bean.getUser("1");
        System.out.println(user);
        //helloWorld();
    }

    private static void helloWorld() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        HelloWord bean = context.getBean(HelloWord.class);
        System.out.println(String.format("name：%s；age：%s；",bean.getName(), bean.getAge()));
    }
}
