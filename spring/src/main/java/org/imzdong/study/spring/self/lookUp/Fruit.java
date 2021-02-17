package org.imzdong.study.spring.self.lookUp;

public interface Fruit {

    default void getFruit(){
        System.out.println("I want to get a fruit!");
    }
}
