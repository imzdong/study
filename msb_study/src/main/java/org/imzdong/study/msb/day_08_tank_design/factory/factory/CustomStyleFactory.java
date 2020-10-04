package org.imzdong.study.msb.day_08_tank_design.factory.factory;

import org.imzdong.study.msb.day_08_tank_design.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_tank_design.factory.product.impl.BlockBoom;
import org.imzdong.study.msb.day_08_tank_design.factory.product.impl.OvalBullet;
import org.imzdong.study.msb.day_08_tank_design.factory.product.impl.SimpleTank;
import org.imzdong.study.msb.day_08_tank_design.model.*;

public class CustomStyleFactory extends AbstractStyleFactory {
    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group) {
        return new SimpleTank(x, y, dir, group);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group) {
        return new OvalBullet(x, y, dir, group) ;
    }

    @Override
    public BaseBoom createBoom(int x, int y) {
        return new BlockBoom(x, y);
    }
}
