package org.imzdong.study.msb.day_08_tank_design.decorate;

import org.imzdong.study.msb.day_08_tank_design.GameObject;

import java.awt.*;

public class TailDecorator extends Decorator {

    public TailDecorator(GameObject gameObject) {
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
        //System.out.println("tail:"+go.getW()+":"+go.getH());
        g.drawLine(go.getX(), go.getY(), go.getX()+ go.getW(), go.getY()+ go.getH());
        g.setColor(color);
    }
}
