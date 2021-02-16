package org.imzdong.study.spring.circleDepend;

import org.imzdong.study.spring.util.SelfSpringUtil;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;
import java.util.Properties;

public class CircleDependMain {

    public static void main(String[] args) throws Exception {
        SelfSpringUtil.saveGeneratedCGlibProxyFiles(System.getProperty("user.dir")+"/proxy");
        ApplicationContext context = new ClassPathXmlApplicationContext("circle-depend.xml");
        A a = context.getBean(A.class);
        System.out.println(a);
        System.out.println(a.getB().getName());
        a.enhanceMethod();

        B b = context.getBean(B.class);
        System.out.println(b);
        System.out.println(b.getA().getName());
        b.enhanceMethod();
    }
}
