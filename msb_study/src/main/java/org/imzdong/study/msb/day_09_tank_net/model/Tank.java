package org.imzdong.study.msb.day_09_tank_net.model;

import org.imzdong.study.msb.day_09_tank_net.TankFrame;
import org.imzdong.study.msb.day_09_tank_net.util.ImageMgr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;

public class Tank {

    private int x;
    private int y;
    private static final int speed = 6;
    private Dir dir;
    private boolean moving = true;
    private TankFrame tankFrame;
    private boolean living = true;
    public int width = ImageMgr.tankD.getWidth();
    public int height = ImageMgr.tankD.getHeight();
    private Random random = new Random();
    private Group group;
    Rectangle rectangleTank;
    private UUID uuid ;

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group, UUID uuid) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.group = group;
        this.uuid = uuid;
        if(group == Group.GOOD){
            moving = false;
        }
        rectangleTank = new Rectangle(x, y, width, height);
    }

    public void paint(Graphics g) {
        if(!living){
            tankFrame.enemyList.remove(this);
        }
        Color color = g.getColor();
        g.setColor(Color.red);
        g.drawString("tank："+uuid,x,y-5);
        g.setColor(color);
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

    public Dir getDir() {
        return dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", tankFrame=" + tankFrame +
                ", living=" + living +
                ", width=" + width +
                ", height=" + height +
                ", random=" + random +
                ", group=" + group +
                ", rectangleTank=" + rectangleTank +
                ", uuid=" + uuid +
                '}';
    }
}
