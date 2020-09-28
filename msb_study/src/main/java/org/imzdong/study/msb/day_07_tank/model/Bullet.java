package org.imzdong.study.msb.day_07_tank.model;

import org.imzdong.study.msb.day_07_tank.TankFrame;

import java.awt.*;
import java.util.List;

public class Bullet {

    private int x;
    private int y;
    private final static int speed = 10;
    private Dir dir;
    private TankFrame tankFrame;
    private boolean live = true;

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        if(!live){
            tankFrame.bullets.remove(this);
        }
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
        if(x < 0 || y < 0 || x > tankFrame.getWidth() || y > tankFrame.getHeight()) {
            live = false;
        }
        List<Tank> enemyList = tankFrame.enemyList;
        for (int i = 0; i < enemyList.size(); i++) {
            enemyList.get(i).dead();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
