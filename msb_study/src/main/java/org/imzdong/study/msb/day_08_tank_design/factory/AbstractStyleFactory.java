package org.imzdong.study.msb.day_08_tank_design.factory;

import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_tank_design.model.Dir;
import org.imzdong.study.msb.day_08_tank_design.model.Group;

public abstract class AbstractStyleFactory {

    public abstract BaseTank createTank(int x, int y, Dir dir, Group group);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group);
    public abstract BaseBoom createBoom(int x, int y);
}
