package org.imzdong.study.msb.day_08_design_tank.factory.product.impl;

import org.imzdong.study.msb.day_08_design_tank.GameModelFacade;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_design_tank.util.Audio;
import org.imzdong.study.msb.day_08_design_tank.util.ImageMgr;

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
