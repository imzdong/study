package org.imzdong.study.msb.day_08_design_tank;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected int x,y,w,h;

    public abstract void paint(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
