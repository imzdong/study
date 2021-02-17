package org.imzdong.study.spring.self.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Person {

    @Autowired
    private GoodMan goodMan;

    private String name;

    @SelfAnnotation
    private Map<String, Object> maps;

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", maps=" + maps +
                '}';
    }
}
