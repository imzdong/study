package org.imzdong.study.msb.day_09_tank_net;

import org.imzdong.study.msb.day_09_tank_net.model.Dir;
import org.imzdong.study.msb.day_09_tank_net.model.Group;
import org.imzdong.study.msb.day_09_tank_net.model.Tank;
import org.imzdong.study.msb.day_09_tank_net.net.Client;
import org.imzdong.study.msb.day_09_tank_net.util.Audio;

public class TankFrameMain {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = TankFrame.getInstance();
        /*for (int i = 0; i < 6; i++) {
            Tank enemy = new Tank(i*100+100, 300, Dir.DOWN, tankFrame, Group.BAD);
            tankFrame.enemyList.add(enemy);
        }*/
        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        new Thread(()->{
            while (true){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tankFrame.repaint();
            }
        }).start();
        Client instance = Client.getInstance();
        instance.connectSync(9090);
    }
}
