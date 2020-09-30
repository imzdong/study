package org.imzdong.study.msb.day_08_design_tank.model;

import org.imzdong.study.msb.day_08_design_tank.GameObject;

import java.awt.*;

public class Wall extends GameObject {

    private int w,h;

    Rectangle rectangleWall;

    public Wall(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        rectangleWall = new Rectangle(x,y,w,h);
    }

    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.cyan);
        g.fillRect(x, y, w, h);
        g.setColor(color);
    }

    public Rectangle getRectangleWall() {
        return rectangleWall;
    }
}
