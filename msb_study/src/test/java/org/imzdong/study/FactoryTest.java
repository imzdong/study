package org.imzdong.study;

import org.imzdong.study.msb.desgin.factory.abs.AbstractFactory;
import org.imzdong.study.msb.desgin.factory.abs.factory.MagicFactory;
import org.imzdong.study.msb.desgin.factory.abs.factory.ModelFactory;
import org.imzdong.study.msb.desgin.factory.method.BusFactory;
import org.imzdong.study.msb.desgin.factory.method.CarFactory;
import org.imzdong.study.msb.desgin.factory.method.MethodFactory;
import org.imzdong.study.msb.desgin.factory.model.Product;
import org.imzdong.study.msb.desgin.factory.simple.ProductType;
import org.imzdong.study.msb.desgin.factory.simple.SimpleFactory;
import org.junit.Test;

/**
 * 工厂模式：
 * 简单工厂模式，提供构建类，不灵活（factory、product(Car、Bus...)）。
 * 工厂方法模式，可扩展，产品任意扩展（factory(CarFactory、BusFactory...)、Product(Car、Bus...)）
 * 抽象工厂，可扩展，产品组任意扩展（factory(Product(Car,House,Vehicle)(ModelFactory、MagicFactory...)、Product(Car,House,Vehicle)）
 * 名词用抽象，形容词用接口
 * 食品：abstract 会跑的：interface movable
 */
public class FactoryTest {

    @Test
    public void simple(){
        SimpleFactory simpleFactory = new SimpleFactory();
        Product car = simpleFactory.create(ProductType.CAR);
        car.name();
        Product plane = simpleFactory.create(ProductType.PLANE);
        plane.name();
    }

    @Test
    public void methodFactory(){
        MethodFactory methodFactory = new BusFactory();
        methodFactory.create().name();
        methodFactory = new CarFactory();
        methodFactory.create().name();
    }

    @Test
    public void abstractFactory(){
        AbstractFactory abstractFactory = new ModelFactory();
        abstractFactory.creatFood().eat();
        abstractFactory.creatHouse().live();
        abstractFactory.creatVehicle().use();
        System.out.println("-------------------------------------");
        abstractFactory = new MagicFactory();
        abstractFactory.creatFood().eat();
        abstractFactory.creatHouse().live();
        abstractFactory.creatVehicle().use();
    }

}
