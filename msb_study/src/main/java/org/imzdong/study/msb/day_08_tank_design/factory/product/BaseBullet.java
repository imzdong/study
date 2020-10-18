package org.imzdong.study.msb.day_08_tank_design.factory.product;

import org.imzdong.study.msb.day_08_tank_design.GameObject;
import org.imzdong.study.msb.day_08_tank_design.model.Group;

import java.awt.*;

public abstract class BaseBullet  extends GameObject {

    public abstract Group getGroup();

    public abstract Rectangle getRectangleBullet();

    public abstract void dead();
}
