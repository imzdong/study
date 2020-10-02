package org.imzdong.study.msb.design.factory.model;

public class Car implements Product {

    @Override
    public void name(){
        System.out.println("I'm Car!");
    }
}
