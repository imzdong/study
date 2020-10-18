package org.imzdong.study.msb.design.factory.abs.product.magic;

import org.imzdong.study.msb.design.factory.abs.product.House;

public class Cave extends House {
    @Override
    public void live() {
        System.out.println("I'm magic House: Cave");
    }
}
