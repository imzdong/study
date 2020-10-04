package org.imzdong.study.msb.day_08_tank_design.factory.product.impl;

import org.imzdong.study.msb.day_08_tank_design.GameModelFacade;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_tank_design.util.Audio;
import org.imzdong.study.msb.day_08_tank_design.util.ImageMgr;

import java.awt.*;

public class BlockBoom extends BaseBoom {

    private int step = 0;

    public BlockBoom(int x, int y) {
        this.x = x;
        this.y = y;
        GameModelFacade.getGm().add(this);
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        g.drawImage(ImageMgr.blockBooms[step++], x, y, null);
        if(step>= ImageMgr.blockBooms.length){
            GameModelFacade.getGm().remove(this);
        }
    }

}
