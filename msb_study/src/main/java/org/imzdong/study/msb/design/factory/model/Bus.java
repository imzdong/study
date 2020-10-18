package org.imzdong.study.msb.design.factory.model;

public class Bus implements Product{
    @Override
    public void name() {
        System.out.println("I'm Bus");
    }
}
