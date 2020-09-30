package org.imzdong.study.msb.day_08_design_tank.collide;

import org.imzdong.study.msb.day_08_design_tank.GameObject;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;

public class TankCollision implements Collator {
    @Override
    public boolean collision(GameObject o1, GameObject o2) {
        if(o1 instanceof BaseTank && o2 instanceof BaseTank){
            BaseTank tank2 = (BaseTank) o2;
            BaseTank tank1 = (BaseTank) o1;
            if(tank1.rectangleTank.intersects(tank2.rectangleTank)){
                tank1.back();
                tank2.back();
            }
        }
        return true;
    }
}

