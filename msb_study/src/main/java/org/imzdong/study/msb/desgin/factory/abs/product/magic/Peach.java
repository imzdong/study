package org.imzdong.study.msb.desgin.factory.abs.product.magic;

import org.imzdong.study.msb.desgin.factory.abs.product.Food;

public class Peach extends Food {
    @Override
    public void eat() {
        System.out.println("I'm magic food: Peach");
    }
}
