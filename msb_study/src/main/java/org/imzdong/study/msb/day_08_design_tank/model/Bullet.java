package org.imzdong.study.msb.day_08_design_tank.model;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.constant.Constant;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_design_tank.util.ImageMgr;

import java.awt.*;

public class Bullet extends BaseBullet {

    private int x;
    private int y;
    private final static int speed = 10;
    private Dir dir;
    private boolean living = true;
    public static int width = ImageMgr.bulletU.getWidth();
    public static int height = ImageMgr.bulletU.getHeight();
    private Group group;
    Rectangle rectangleBullet;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        rectangleBullet = new Rectangle(x, y, width, height);
        GameModelFacade.getGm().add(this);
    }

    public void paint(Graphics g) {
        if(!living){
            GameModelFacade.getGm().remove(this);
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
        if(x < 0 || y < 0 || x > Constant.frameWith || y > Constant.frameHeight) {
            living = false;
        }
        rectangleBullet.x = x;
        rectangleBullet.y = y;
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

    public Group getGroup() {
        return group;
    }

    @Override
    public Rectangle getRectangleBullet() {
        return rectangleBullet;
    }
}
