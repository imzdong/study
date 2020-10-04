package org.imzdong.study.msb.day_08_tank_design.model;

import org.imzdong.study.msb.day_08_tank_design.GameModelFacade;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_tank_design.util.Audio;
import org.imzdong.study.msb.day_08_tank_design.util.ImageMgr;

import java.awt.*;

public class Boom extends BaseBoom {

    private int step = 0;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
        GameModelFacade.getGm().add(this);
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        g.drawImage(ImageMgr.booms[step++], x, y, null);
        if(step>= ImageMgr.booms.length){
            GameModelFacade.getGm().remove(this);
        }
    }

}
