package org.imzdong.study.msb.design.factory.abs.factory;

import org.imzdong.study.msb.design.factory.abs.AbstractFactory;
import org.imzdong.study.msb.design.factory.abs.product.Food;
import org.imzdong.study.msb.design.factory.abs.product.House;
import org.imzdong.study.msb.design.factory.abs.product.Vehicle;
import org.imzdong.study.msb.design.factory.abs.product.model.Car;
import org.imzdong.study.msb.design.factory.abs.product.model.Farmyard;
import org.imzdong.study.msb.design.factory.abs.product.model.Wheat;

public class ModelFactory extends AbstractFactory {
    @Override
    public Food creatFood() {
        return new Wheat();
    }

    @Override
    public House creatHouse() {
        return new Farmyard();
    }

    @Override
    public Vehicle creatVehicle() {
        return new Car();
    }
}
