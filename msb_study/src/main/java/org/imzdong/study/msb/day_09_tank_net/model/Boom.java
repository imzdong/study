package org.imzdong.study.msb.day_09_tank_net.model;

import org.imzdong.study.msb.day_09_tank_net.TankFrame;
import org.imzdong.study.msb.day_09_tank_net.util.Audio;
import org.imzdong.study.msb.day_09_tank_net.util.ImageMgr;

import java.awt.*;

public class Boom {

    private int x;
    private int y;
    private TankFrame tankFrame;
    private int step = 0;

    public Boom(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        g.drawImage(ImageMgr.booms[step++], x, y, null);
        if(step>= ImageMgr.booms.length){
            tankFrame.booms.remove(this);
        }

    }

}
