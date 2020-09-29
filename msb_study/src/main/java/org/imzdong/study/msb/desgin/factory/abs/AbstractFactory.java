package org.imzdong.study.msb.desgin.factory.abs;

import org.imzdong.study.msb.desgin.factory.abs.product.Food;
import org.imzdong.study.msb.desgin.factory.abs.product.House;
import org.imzdong.study.msb.desgin.factory.abs.product.Vehicle;

/**
 * 名词用抽象，形容词用接口
 * 食品：abstract 会跑的：interface movable
 * 一组产品，产品组的扩展
 *
 */
public abstract class AbstractFactory {

    public abstract Food creatFood();
    public abstract House creatHouse();
    public abstract Vehicle creatVehicle();
}
