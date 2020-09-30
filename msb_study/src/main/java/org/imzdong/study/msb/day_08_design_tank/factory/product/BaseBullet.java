package org.imzdong.study.msb.day_08_design_tank.factory.product;

import java.awt.*;

public abstract class BaseBullet {
    public abstract void paint(Graphics g);

    public abstract void collision(BaseTank baseTank);
}
