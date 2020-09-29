package org.imzdong.study.msb.desgin.factory.simple;

import org.imzdong.study.msb.desgin.factory.model.Car;
import org.imzdong.study.msb.desgin.factory.model.Plane;
import org.imzdong.study.msb.desgin.factory.model.Product;

/**
 * 简单工厂方法：可以控制对象的创建，在创建前后做一些准备及统计工作
 * 可扩展不中
 * @author Winter
 * @see Integer valueOf()
 */
public class SimpleFactory {

    public Product create(ProductType productType){
        switch (productType){
            case CAR:
                return new Car();
            case PLANE:
                return new Plane();
        }
        return null;
    }

}
