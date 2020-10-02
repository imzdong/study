package org.imzdong.study.msb.design.factory.abs.product.model;

import org.imzdong.study.msb.design.factory.abs.product.Food;

public class Wheat extends Food {
    @Override
    public void eat() {
        System.out.println("I'm model food: wheat");
    }
}
