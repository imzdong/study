package org.imzdong.study.msb.day_08_tank_design.collide;

import org.imzdong.study.msb.day_08_tank_design.GameObject;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_tank_design.model.Wall;

public class TankWallCollision implements Collator {
    @Override
    public boolean collision(GameObject o1, GameObject o2) {
        if(o1 instanceof BaseTank && o2 instanceof Wall){
            Wall tank2 = (Wall) o2;
            BaseTank tank1 = (BaseTank) o1;
            if(tank1.rectangleTank.intersects(tank2.getRectangleWall())){
                tank1.back();
            }
        }else if(o2 instanceof BaseTank && o1 instanceof Wall){
            collision(o2, o1);
        }
        return true;
    }
}

