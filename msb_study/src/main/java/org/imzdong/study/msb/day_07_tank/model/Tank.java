package org.imzdong.study.msb.day_07_tank.model;

import org.imzdong.study.msb.day_07_tank.TankFrame;
import org.imzdong.study.msb.day_07_tank.util.ImageMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Tank {

    private int x;
    private int y;
    private static final int speed = 5;
    private Dir dir;
    private boolean moving;
    private TankFrame tankFrame;
    private boolean living = true;
    public int width = ImageMgr.tankD.getWidth();
    public int height = ImageMgr.tankD.getHeight();

    public Tank(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        if(!living){
            tankFrame.enemyList.remove(this);
        }
        BufferedImage bi = null;
        switch (dir) {
            case UP:
                bi = ImageMgr.tankU;
                break;
            case DOWN:
                bi = ImageMgr.tankD;
                break;
            case LEFT:
                bi = ImageMgr.tankL;
                break;
            case RIGHT:
                bi = ImageMgr.tankR;
                break;
            default:
                break;
        }
        g.drawImage(bi, x, y, null);
        if(moving) {
            move();
        }
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

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void fire() {
        int bulletX = this.x + this.width/2 - Bullet.width/2;
        int bulletY = this.y + this.height/2 - Bullet.height/2;
        tankFrame.bullets.add(new Bullet(bulletX, bulletY, dir,tankFrame));
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

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", tankFrame=" + tankFrame +
                '}';
    }
}
