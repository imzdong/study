package org.imzdong.study.spring.self.factoryMethod;

public class FactoryMethodStaticUser {

    public static FactoryUser getFactoryUser(){
        return new FactoryUser("FactoryMethodStaticUser");
    }
}
