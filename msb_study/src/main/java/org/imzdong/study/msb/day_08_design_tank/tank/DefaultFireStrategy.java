package org.imzdong.study.msb.day_08_design_tank.tank;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.decorate.RectDecorator;
import org.imzdong.study.msb.day_08_design_tank.decorate.TailDecorator;
import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.util.ImageMgr;

public class DefaultFireStrategy implements FireStrategy<BaseTank>{

    private static DefaultFireStrategy defaultFireStrategy = new DefaultFireStrategy();

    private DefaultFireStrategy(){}

    public static DefaultFireStrategy getInstance(){
        return defaultFireStrategy;
    }

    @Override
    public void fire(BaseTank tank) {
        GameModelFacade gm = GameModelFacade.getGm();
        int bulletX = tank.getX() + tank.getW()/2 - ImageMgr.bulletU.getWidth()/2;
        int bulletY = tank.getY() + tank.getH()/2 - ImageMgr.bulletU.getHeight()/2;
        AbstractStyleFactory abstractStyleFactory = gm.abstractStyleFactory;
        abstractStyleFactory.createBullet(bulletX, bulletY, tank.getDir(), tank.getGroup());
        /*gm.add(new TailDecorator(
                new RectDecorator(
                        abstractStyleFactory.createBullet(bulletX, bulletY, tank.getDir(), tank.getGroup())
                )));*/
    }
}
