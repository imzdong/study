package org.imzdong.study.msb.day_08_design_tank.factory.product;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.model.Group;
import org.imzdong.study.msb.day_08_design_tank.tank.DefaultFireStrategy;
import org.imzdong.study.msb.day_08_design_tank.tank.FireStrategy;
import org.imzdong.study.msb.day_08_design_tank.util.ImageMgr;

import java.awt.*;

public abstract class BaseTank {

    protected FireStrategy<BaseTank> fireStrategy = DefaultFireStrategy.getInstance();
    public Rectangle rectangleTank = new Rectangle();
    public int width = ImageMgr.tankD.getWidth();
    public int height = ImageMgr.tankD.getHeight();

    public abstract void paint(Graphics g);

    public abstract void fire();

    public abstract void setMoving(boolean b);

    public abstract void setDir(Dir up);

    public abstract Group getGroup();

    public abstract void dead();

    public abstract int getX();

    public abstract int getY();

    public abstract Dir getDir();

    public abstract GameModelFacade getGm();
}
