package org.imzdong.study.msb.day_07_tank;

import org.imzdong.study.msb.day_07_tank.model.Dir;
import org.imzdong.study.msb.day_07_tank.model.Group;
import org.imzdong.study.msb.day_07_tank.model.Tank;
import org.imzdong.study.msb.day_07_tank.util.Audio;

public class TankFrameMain {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        for (int i = 0; i < 6; i++) {
            Tank enemy = new Tank(i*60+100, 100, Dir.DOWN, tankFrame, Group.BAD);
            tankFrame.enemyList.add(enemy);
        }
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
