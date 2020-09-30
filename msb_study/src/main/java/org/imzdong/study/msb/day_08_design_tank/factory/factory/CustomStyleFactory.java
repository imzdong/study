package org.imzdong.study.msb.day_08_design_tank.factory.factory;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.factory.product.impl.BlockBoom;
import org.imzdong.study.msb.day_08_design_tank.factory.product.impl.OvalBullet;
import org.imzdong.study.msb.day_08_design_tank.factory.product.impl.SimpleTank;
import org.imzdong.study.msb.day_08_design_tank.model.*;

public class CustomStyleFactory extends AbstractStyleFactory {
    @Override
    public BaseTank createTank(int x, int y, Dir dir, GameModelFacade gm, Group group) {
        return new SimpleTank(x, y, dir, gm, group);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, GameModelFacade gm, Group group) {
        return new OvalBullet(x, y, dir, gm, group) ;
    }

    @Override
    public BaseBoom createBoom(int x, int y, GameModelFacade gm) {
        return new BlockBoom(x, y, gm);
    }
}
