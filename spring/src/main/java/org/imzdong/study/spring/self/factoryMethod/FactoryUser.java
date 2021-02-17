package org.imzdong.study.spring.self.factoryMethod;

public class FactoryUser {

    public String name;

    public FactoryUser() {
    }

    public FactoryUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FactoryUser{" +
                "name='" + name + '\'' +
                '}';
    }
}
