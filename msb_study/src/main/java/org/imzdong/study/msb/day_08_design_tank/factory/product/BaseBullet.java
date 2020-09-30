package org.imzdong.study.msb.day_08_design_tank.factory.product;

import org.imzdong.study.msb.day_08_design_tank.GameObject;
import org.imzdong.study.msb.day_08_design_tank.model.Group;

import java.awt.*;

public abstract class BaseBullet  extends GameObject {

    public abstract Group getGroup();

    public abstract Rectangle getRectangleBullet();

    public abstract void dead();
}
