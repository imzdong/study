package org.imzdong.study.msb.day_08_design_tank.factory.factory;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.Boom;
import org.imzdong.study.msb.day_08_design_tank.model.Bullet;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.model.Group;
import org.imzdong.study.msb.day_08_design_tank.model.Tank;

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
