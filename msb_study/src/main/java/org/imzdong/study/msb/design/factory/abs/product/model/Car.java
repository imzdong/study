package org.imzdong.study.msb.design.factory.abs.product.model;

import org.imzdong.study.msb.design.factory.abs.product.Vehicle;

public class Car extends Vehicle {
    @Override
    public void use() {
        System.out.println("I'm model Vehicle: car");
    }
}
