package org.imzdong.study.msb.day_08_tank_design.model;

import org.imzdong.study.msb.day_08_tank_design.GameModelFacade;
import org.imzdong.study.msb.day_08_tank_design.constant.Constant;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_tank_design.util.ImageMgr;

import java.awt.*;

public class Bullet extends BaseBullet {

    private final static int speed = 10;
    private Dir dir;
    private boolean living = true;
    private Group group;
    Rectangle rectangleBullet;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        w = ImageMgr.bulletU.getWidth();
        h = ImageMgr.bulletU.getHeight();
        rectangleBullet = new Rectangle(x, y, w, h);
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
