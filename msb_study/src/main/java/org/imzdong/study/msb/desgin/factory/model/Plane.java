package org.imzdong.study.msb.desgin.factory.model;

public class Plane implements Product {
    @Override
    public void name() {
        System.out.println("I'm Plane!");
    }
}
