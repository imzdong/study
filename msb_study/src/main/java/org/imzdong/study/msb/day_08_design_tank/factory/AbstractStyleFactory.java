package org.imzdong.study.msb.day_08_design_tank.factory;

import org.imzdong.study.msb.day_08_design_tank.TankFrame;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.model.Group;

public abstract class AbstractStyleFactory {

    public abstract BaseTank createTank(int x, int y, Dir dir, TankFrame tankFrame, Group group);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, TankFrame tankFrame, Group group);
    public abstract BaseBoom createBoom(int x, int y, TankFrame tankFrame);
}
