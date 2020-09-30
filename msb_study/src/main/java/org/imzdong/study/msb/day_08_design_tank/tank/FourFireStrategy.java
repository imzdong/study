package org.imzdong.study.msb.day_08_design_tank.tank;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.Bullet;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;

/**
 * DoubleCheck单例模式
 */
public class FourFireStrategy implements FireStrategy<BaseTank>{

    private static volatile FourFireStrategy fourFireStrategy;
    private final static byte[] lock = new byte[10];

    private FourFireStrategy(){}

    public static FourFireStrategy getInstance(){
        if(fourFireStrategy == null){
            synchronized (lock){
                if(fourFireStrategy == null){
                    fourFireStrategy = new FourFireStrategy();
                }
            }
        }
        return fourFireStrategy;
    }

    @Override
    public void fire(BaseTank tank) {
        GameModelFacade gm = GameModelFacade.getGm();
        AbstractStyleFactory abstractStyleFactory = gm.abstractStyleFactory;
        int bulletX = tank.getX() + tank.width/2 - Bullet.width/2;
        int bulletY = tank.getY() + tank.height/2 - Bullet.height/2;
        for(Dir dir:Dir.values()) {
            abstractStyleFactory.createBullet(bulletX, bulletY, dir,tank.getGroup());
        }
    }
}
