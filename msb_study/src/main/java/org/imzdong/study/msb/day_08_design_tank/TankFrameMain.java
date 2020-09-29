package org.imzdong.study.msb.day_08_design_tank;

import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.model.Group;
import org.imzdong.study.msb.day_08_design_tank.tank.Tank;
import org.imzdong.study.msb.day_08_design_tank.util.Audio;
import org.imzdong.study.msb.day_08_design_tank.util.PropertyMgr;

public class TankFrameMain {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        int initEnemyCount = PropertyMgr.getInt("initEnemyCount");
        int enemySpace = PropertyMgr.getInt("enemySpace");
        for (int i = 0; i < initEnemyCount; i++) {
            Tank enemy = new Tank(i*enemySpace+50, 300, Dir.DOWN, tankFrame, Group.BAD);
            tankFrame.enemyList.add(enemy);
        }
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
