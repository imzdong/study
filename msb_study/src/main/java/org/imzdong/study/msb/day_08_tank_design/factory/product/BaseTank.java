package org.imzdong.study.msb.day_08_tank_design.factory.product;

import org.imzdong.study.msb.day_08_tank_design.GameObject;
import org.imzdong.study.msb.day_08_tank_design.model.Dir;
import org.imzdong.study.msb.day_08_tank_design.model.Group;
import org.imzdong.study.msb.day_08_tank_design.tank.DefaultFireStrategy;
import org.imzdong.study.msb.day_08_tank_design.tank.FireStrategy;

import java.awt.*;

public abstract class BaseTank extends GameObject {

    protected FireStrategy<BaseTank> fireStrategy = DefaultFireStrategy.getInstance();
    public Rectangle rectangleTank = new Rectangle();
    protected int oldX,oldY;

    public abstract void fire();

    public abstract void setMoving(boolean b);

    public abstract void setDir(Dir up);

    public abstract Group getGroup();

    public abstract void dead();

    public abstract Dir getDir();

    public void back(){
        this.x = oldX;
        this.y = oldY;
    }
}
