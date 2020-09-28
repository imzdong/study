package org.imzdong.study.msb.day_07_tank.model;

import java.awt.*;

public class Tank {

    private int x;
    private int y;
    private int speed = 10;
    private Dir dir;
    private boolean moving;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) {
        g.fillRect(x, y,50,50);
        if(moving) {
            switch (dir) {
                case UP:
                    y -= 10;
                    break;
                case DOWN:
                    y += 10;
                    break;
                case LEFT:
                    x -= 10;
                    break;
                case RIGHT:
                    x += 10;
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
