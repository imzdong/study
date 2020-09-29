package org.imzdong.study.msb.desgin.factory.method;

import org.imzdong.study.msb.desgin.factory.model.Bus;
import org.imzdong.study.msb.desgin.factory.model.Product;

public class BusFactory implements MethodFactory{
    @Override
    public Product create() {
        System.out.println("I'm BusFactory");
        return new Bus();
    }
}
