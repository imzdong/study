package org.imzdong.study.msb.day_08_tank_design.decorate;

import org.imzdong.study.msb.day_08_tank_design.GameObject;

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
