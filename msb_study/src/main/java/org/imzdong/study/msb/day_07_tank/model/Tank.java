package org.imzdong.study.msb.day_07_tank.model;

import org.imzdong.study.msb.day_07_tank.TankFrame;
import org.imzdong.study.msb.day_07_tank.util.ImageMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tank {

    private int x;
    private int y;
    private static final int speed = 2;
    private Dir dir;
    private boolean moving = true;
    private TankFrame tankFrame;
    private boolean living = true;
    public int width = ImageMgr.tankD.getWidth();
    public int height = ImageMgr.tankD.getHeight();
    private Random random = new Random();
    private Group group = Group.BAD;

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
    }

    public void paint(Graphics g) {
        if(!living){
            tankFrame.enemyList.remove(this);
        }
        BufferedImage bi = null;
        switch (dir) {
            case UP:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.tankU;
                }else {
                    bi = ImageMgr.badTankU;
                }
                break;
            case DOWN:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.tankD;
                }else {
                    bi = ImageMgr.badTankD;
                }
                break;
            case LEFT:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.tankL;
                }else {
                    bi = ImageMgr.badTankL;
                }
                break;
            case RIGHT:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.tankR;
                }else {
                    bi = ImageMgr.badTankR;
                }
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
        if(random.nextInt(10)>8){
            fire();
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
        tankFrame.bullets.add(new Bullet(bulletX, bulletY, dir,tankFrame,this.group));
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

    public Group getGroup() {
        return group;
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
