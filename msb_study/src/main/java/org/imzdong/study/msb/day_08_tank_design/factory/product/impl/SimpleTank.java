package org.imzdong.study.msb.day_08_tank_design.factory.product.impl;

import org.imzdong.study.msb.day_08_tank_design.GameModelFacade;
import org.imzdong.study.msb.day_08_tank_design.constant.Constant;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_tank_design.model.Dir;
import org.imzdong.study.msb.day_08_tank_design.model.Group;
import org.imzdong.study.msb.day_08_tank_design.tank.FourFireStrategy;
import org.imzdong.study.msb.day_08_tank_design.util.ImageMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SimpleTank extends BaseTank {

    private static final int speed = 5;
    private Dir dir;
    private boolean moving = true;
    private boolean living = true;
    private Random random = new Random();
    private Group group;
    public SimpleTank(int x, int y, Dir dir,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        if(group == Group.GOOD){
            moving = false;
            fireStrategy = FourFireStrategy.getInstance();
        }
        rectangleTank.x = x;
        rectangleTank.y = y;
        w = ImageMgr.simpleTankU.getWidth();
        h = ImageMgr.simpleTankU.getHeight();
        rectangleTank.width = w;
        rectangleTank.height = h;
        if(group != Group.GOOD) {
            GameModelFacade.getGm().add(this);
        }
    }

    public void paint(Graphics g) {
        if(!living){
            GameModelFacade.getGm().remove(this);
        }
        BufferedImage bi = null;
        switch (dir) {
            case UP:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.simpleTankU;
                }else {
                    bi = ImageMgr.simpleBadTankU;
                }
                break;
            case DOWN:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.simpleTankD;
                }else {
                    bi = ImageMgr.simpleBadTankD;
                }
                break;
            case LEFT:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.simpleTankL;
                }else {
                    bi = ImageMgr.simpleBadTankL;
                }
                break;
            case RIGHT:
                if(group.equals(Group.GOOD)) {
                    bi = ImageMgr.simpleTankR;
                }else {
                    bi = ImageMgr.simpleBadTankR;
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
        this.oldX = x;
        this.oldY = y;
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
        if(x > (Constant.frameWith - w -2)){
            x = Constant.frameWith - w -2;
        }
        if(y > (Constant.frameHeight - h -2)){
            y = Constant.frameHeight - h -2;
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
                '}';
    }
}
