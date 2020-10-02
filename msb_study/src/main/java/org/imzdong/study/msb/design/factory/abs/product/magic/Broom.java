package org.imzdong.study.msb.design.factory.abs.product.magic;

import org.imzdong.study.msb.design.factory.abs.product.Vehicle;

public class Broom extends Vehicle {
    @Override
    public void use() {
        System.out.println("I'm magic Vehicle: Broom");
    }
}
