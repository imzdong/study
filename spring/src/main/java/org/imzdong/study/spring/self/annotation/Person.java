package org.imzdong.study.spring.self.annotation;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Person {

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
