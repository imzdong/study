package org.imzdong.study.msb.day_08_tank_design.decorate;

import org.imzdong.study.msb.day_08_tank_design.GameObject;

import java.awt.*;

public class RectDecorator extends Decorator {

    public RectDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics g) {
        this.x = go.getX();
        this.y = go.getY();
        this.w = go.getW();
        this.h = go.getH();
        go.paint(g);
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawRect(go.getX(), go.getY(), go.getW(), go.getH());
        g.setColor(color);
    }
}
