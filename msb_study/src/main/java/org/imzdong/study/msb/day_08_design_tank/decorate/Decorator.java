package org.imzdong.study.msb.day_08_design_tank.decorate;

import org.imzdong.study.msb.day_08_design_tank.GameObject;

import java.awt.*;
import java.io.Writer;

/**
 * @see Writer
 */
public abstract class Decorator extends GameObject {

    protected GameObject go;

    public Decorator(GameObject go){
        this.go = go;
    }

    @Override
    public void paint(Graphics g) {
        go.paint(g);
    }
}
