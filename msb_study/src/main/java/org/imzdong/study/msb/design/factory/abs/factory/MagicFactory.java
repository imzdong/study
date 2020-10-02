package org.imzdong.study.msb.design.factory.abs.factory;

import org.imzdong.study.msb.design.factory.abs.AbstractFactory;
import org.imzdong.study.msb.design.factory.abs.product.Food;
import org.imzdong.study.msb.design.factory.abs.product.House;
import org.imzdong.study.msb.design.factory.abs.product.Vehicle;
import org.imzdong.study.msb.design.factory.abs.product.magic.Broom;
import org.imzdong.study.msb.design.factory.abs.product.magic.Cave;
import org.imzdong.study.msb.design.factory.abs.product.magic.Peach;

public class MagicFactory extends AbstractFactory {
    @Override
    public Food creatFood() {
        return new Peach();
    }

    @Override
    public House creatHouse() {
        return new Cave();
    }

    @Override
    public Vehicle creatVehicle() {
        return new Broom();
    }
}
