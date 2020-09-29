package org.imzdong.study.msb.desgin.factory.abs.product.model;

import org.imzdong.study.msb.desgin.factory.abs.product.House;

public class Farmyard extends House {
    @Override
    public void live() {
        System.out.println("I'm model House: Farmyard");
    }
}
