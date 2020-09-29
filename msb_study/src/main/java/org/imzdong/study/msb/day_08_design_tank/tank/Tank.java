package org.imzdong.study.msb.day_08_design_tank.tank;

import org.imzdong.study.msb.day_08_design_tank.TankFrame;
import org.imzdong.study.msb.day_08_design_tank.model.Bullet;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.model.Group;
import org.imzdong.study.msb.day_08_design_tank.util.ImageMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tank {

    private int x;
    private int y;
    private static final int speed = 5;
    private Dir dir;
    private boolean moving = true;
    private TankFrame tankFrame;
    private boolean living = true;
    public int width = ImageMgr.tankD.getWidth();
    public int height = ImageMgr.tankD.getHeight();
    private Random random = new Random();
    private Group group;
    public Rectangle rectangleTank;
    FireStrategy<Tank> fireStrategy = DefaultFireStrategy.getInstance();

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
        if(group == Group.GOOD){
            moving = false;
            fireStrategy = FourFireStrategy.getInstance();
        }
        rectangleTank = new Rectangle(x, y, width, height);
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
        if(group.equals(Group.BAD) && random.nextInt(100)>95){
            fire();
        }
        if(group.equals(Group.BAD) && random.nextInt(100)>95){
            dir = Dir.values()[random.nextInt(4)];
        }
        //边界检测
        boundCheck();
        rectangleTank.x = x;
        rectangleTank.y = y;
    }

    private void boundCheck(){
        if(x < 25){
            x = 20 ;
        }
        if(y < 25){
            y = 20 ;
        }
        if(x > (tankFrame.getWidth() - width -2)){
            x = tankFrame.getWidth() - width -2;
        }
        if(y > (tankFrame.getHeight() - height -2)){
            y = tankFrame.getHeight() - height -2;
        }
    }


    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void fire() {
        fireStrategy.fire(this);
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
