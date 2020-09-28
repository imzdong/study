package org.imzdong.study.msb.day_07_tank.model;

import org.imzdong.study.msb.day_07_tank.TankFrame;

import java.awt.*;
import java.util.List;

public class Tank {

    private int x;
    private int y;
    private static final int speed = 5;
    private Dir dir;
    private boolean moving;
    private TankFrame tankFrame;
    private Color tankColor;
    private boolean live = true;

    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Color color) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
        this.tankColor = color;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(tankColor);
        g.fillRect(x, y,50,50);
        g.setColor(color);
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
        tankFrame.bullets.add(new Bullet(x, y, dir,tankFrame));
    }

    public void dead(){
        List<Bullet> bullets = tankFrame.bullets;
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(bullet.isLive()) {
                int bulletX = bullet.getX();
                int bulletY = bullet.getY();
                if (bulletX >= this.x && bulletX <= x+50 && bulletY >= this.y && bulletY <= y+50) {
                    bullet.setLive(false);
                    this.live = false;
                }
            }
        }
    }

    public boolean isLive() {
        return live;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", tankFrame=" + tankFrame +
                ", tankColor=" + tankColor +
                ", live=" + live +
                '}';
    }
}
