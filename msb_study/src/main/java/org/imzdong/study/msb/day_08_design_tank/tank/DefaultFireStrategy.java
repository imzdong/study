package org.imzdong.study.msb.day_08_design_tank.tank;

import org.imzdong.study.msb.day_08_design_tank.TankFrame;
import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.Bullet;

public class DefaultFireStrategy implements FireStrategy<BaseTank>{

    private static DefaultFireStrategy defaultFireStrategy = new DefaultFireStrategy();

    private DefaultFireStrategy(){}

    public static DefaultFireStrategy getInstance(){
        return defaultFireStrategy;
    }

    @Override
    public void fire(BaseTank tank) {
        TankFrame tankFrame = tank.getTankFrame();
        int bulletX = tank.getX() + tank.width/2 - Bullet.width/2;
        int bulletY = tank.getY() + tank.height/2 - Bullet.height/2;
        AbstractStyleFactory abstractStyleFactory = tankFrame.abstractStyleFactory;
        abstractStyleFactory.createBullet(bulletX, bulletY, tank.getDir(), tankFrame, tank.getGroup());
    }
}
