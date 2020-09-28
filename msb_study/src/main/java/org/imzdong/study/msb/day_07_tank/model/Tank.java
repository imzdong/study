package org.imzdong.study.msb.day_07_tank.model;

import java.awt.*;

public class Tank {

    private int x;
    private int y;
    private static final int speed = 5;
    private Dir dir;
    private boolean moving;

    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.green);
        g.fillRect(x, y,50,50);
        g.setColor(color);
        if(moving) {
            switch (dir) {
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
                default:
                    break;
            }
        }
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
