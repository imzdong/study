package org.imzdong.study.msb.day_08_design_tank.tank;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.util.ImageMgr;

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
        int bulletX = tank.getX() + tank.getW()/2 - ImageMgr.bulletU.getWidth()/2;
        int bulletY = tank.getY() + tank.getH()/2 - ImageMgr.bulletU.getHeight()/2;
        for(Dir dir:Dir.values()) {
            abstractStyleFactory.createBullet(bulletX, bulletY, dir,tank.getGroup());
        }
    }
}
