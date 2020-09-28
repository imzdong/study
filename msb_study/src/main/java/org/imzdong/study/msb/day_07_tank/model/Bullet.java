package org.imzdong.study.msb.day_07_tank.model;

import org.imzdong.study.msb.day_07_tank.TankFrame;
import org.imzdong.study.msb.day_07_tank.util.ImageMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Bullet {

    private int x;
    private int y;
    private final static int speed = 10;
    private Dir dir;
    private TankFrame tankFrame;
    private boolean living = true;
    public static int width = ImageMgr.bulletU.getWidth();
    public static int height = ImageMgr.bulletU.getHeight();
    private Group group = Group.BAD;

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
    }

    public void paint(Graphics g) {
        if(!living){
            tankFrame.bullets.remove(this);
        }
        switch (dir) {
            case UP:
                g.drawImage(ImageMgr.bulletU, x, y, null);
                break;
            case DOWN:
                g.drawImage(ImageMgr.bulletD, x, y, null);
                break;
            case LEFT:
                g.drawImage(ImageMgr.bulletL, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ImageMgr.bulletR, x, y, null);
                break;
            default:
                break;
        }
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
            living = false;
        }
    }
    public void dead(){
        living = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLive() {
        return living;
    }

    public void setLive(boolean live) {
        this.living = live;
    }

    public void collision(Tank tank) {
        if(tank.getGroup().equals(this.group)){
            return;
        }
        Rectangle rectangleTank = new Rectangle(tank.getX(), tank.getY(), tank.width, tank.height);
        Rectangle rectangleBullet = new Rectangle(this.x, this.y, this.width, this.height);
        if(rectangleBullet.intersects(rectangleTank)){
            tank.dead();
            this.dead();
        }
    }

    public Group getGroup() {
        return group;
    }
}
