package org.imzdong.study.msb.day_08_design_tank.collide;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.GameObject;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;

public class BulletCollision implements Collator {
    @Override
    public boolean collision(GameObject o1, GameObject o2) {
        if(o1 instanceof BaseTank && o2 instanceof BaseBullet){
            BaseBullet bullet = (BaseBullet) o2;
            BaseTank tank = (BaseTank) o1;
            if(tank.getGroup().equals(bullet.getGroup())){
                return true;
            }
            if(bullet.getRectangleBullet().intersects(tank.rectangleTank)){
                GameModelFacade gm = GameModelFacade.getGm();
                tank.dead();
                bullet.dead();
                gm.abstractStyleFactory.createBoom(tank.getX()+tank.width/2,tank.getY()+tank.height/2);
                return false;
            }
        }else if(o2 instanceof BaseTank && o1 instanceof BaseBullet){
            return collision(o2, o1);
        }
        return true;
    }
}
