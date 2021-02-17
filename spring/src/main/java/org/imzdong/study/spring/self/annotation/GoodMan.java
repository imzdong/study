package org.imzdong.study.spring.self.annotation;

import org.springframework.beans.factory.InitializingBean;

public class GoodMan implements InitializingBean {

    private String name;

    public GoodMan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("我就是被调用了！！！！-----");
        this.name = "不喜欢了么";
    }

    @Override
    public String toString() {
        return "GoodMan{" +
                "name='" + name + '\'' +
                '}';
    }
}
