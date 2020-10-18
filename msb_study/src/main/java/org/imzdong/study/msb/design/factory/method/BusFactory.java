package org.imzdong.study.msb.design.factory.method;

import org.imzdong.study.msb.design.factory.model.Bus;
import org.imzdong.study.msb.design.factory.model.Product;

public class BusFactory implements MethodFactory{
    @Override
    public Product create() {
        System.out.println("I'm BusFactory");
        return new Bus();
    }
}
