package org.imzdong.study.spring.self.factoryMethod;

public class FactoryMethodUser {

    public FactoryUser factoryUser(){
        return new FactoryUser("FactoryMethodUser");
    }
}
