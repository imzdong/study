package org.imzdong.study.spring.scope;

import org.imzdong.study.spring.ioc.di.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @description:
 * @author: Winter
 * @time: 2020/4/5
 */
@Component
public class SpringScopeDemo implements DisposableBean {

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;
    @Autowired
    @Qualifier("singletonUser")
    private User singletonUserOne;
    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;
    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUserOne;
    @Autowired
    private Collection<User> users;
    @Autowired
    private Map<String, User> userMap;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.imzdong.study.spring.scope");
        context.addBeanFactoryPostProcessor(
                beanFactory ->
                    beanFactory.addBeanPostProcessor(new BeanPostProcessor() {

                        @Override
                        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                            System.out.printf("%s Bean 名称:%s 在初始化后回调...%n", bean.getClass().getName(), beanName);
                            return bean;
                        }
                    })
        );
        context.refresh();
        SpringScopeDemo bean = context.getBean(SpringScopeDemo.class);
        System.out.printf("singletonUser:%s%n",bean.singletonUser);
        System.out.printf("singletonUserOne:%s%n",bean.singletonUserOne);
        System.out.printf("prototypeUser:%s%n",bean.prototypeUser);
        System.out.printf("prototypeUserOne:%s%n",bean.prototypeUserOne);
        System.out.printf("users:%s%n",bean.users);
        System.out.printf("userMap:%s%n",bean.userMap);
        context.close();
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("SpringScopeDemo销毁");
        this.prototypeUser.destroy();
        this.prototypeUserOne.destroy();
    }
}
