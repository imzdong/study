package org.imzdong.study.msb.day_08_tank_design.factory.factory;

import org.imzdong.study.msb.day_08_tank_design.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_tank_design.model.Boom;
import org.imzdong.study.msb.day_08_tank_design.model.Bullet;
import org.imzdong.study.msb.day_08_tank_design.model.Dir;
import org.imzdong.study.msb.day_08_tank_design.model.Group;
import org.imzdong.study.msb.day_08_tank_design.model.Tank;

public class DefaultStyleFactory extends AbstractStyleFactory {
    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group) {
        return new Tank(x, y, dir, group);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group) {
        return new Bullet(x, y, dir, group) ;
    }

    @Override
    public BaseBoom createBoom(int x, int y) {
        return new Boom(x, y);
    }
}
