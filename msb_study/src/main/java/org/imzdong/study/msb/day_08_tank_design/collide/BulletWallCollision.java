package org.imzdong.study.msb.day_08_tank_design.collide;

import org.imzdong.study.msb.day_08_tank_design.GameObject;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_tank_design.model.Wall;

public class BulletWallCollision implements Collator {
    @Override
    public boolean collision(GameObject o1, GameObject o2) {
        if(o1 instanceof Wall && o2 instanceof BaseBullet){
            BaseBullet bullet = (BaseBullet) o2;
            Wall wall = (Wall) o1;
            if(wall.getRectangleWall().intersects(bullet.getRectangleBullet())){
                bullet.dead();
            }
        }else if(o2 instanceof Wall && o1 instanceof BaseBullet){
            return collision(o2, o1);
        }
        return true;
    }
}