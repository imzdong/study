package org.imzdong.study.msb.day_07_tank.model;

import org.imzdong.study.msb.day_07_tank.TankFrame;

import java.awt.*;

public class Bullet {

    private int x;
    private int y;
    private final static int speed = 10;
    private Dir dir;
    private TankFrame tankFrame;

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.red);
        g.fillRect(x, y,10,10);
        g.setColor(color);
        move();
    }

    private void move() {
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

    public void setDir(Dir dir) {
        this.dir = dir;
    }
}
