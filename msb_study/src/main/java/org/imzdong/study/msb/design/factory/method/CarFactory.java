package org.imzdong.study.msb.design.factory.method;

import org.imzdong.study.msb.design.factory.model.Car;
import org.imzdong.study.msb.design.factory.model.Product;

public class CarFactory implements MethodFactory{
    @Override
    public Product create() {
        System.out.println("I'm CarFactory!");
        return new Car();
    }
}
