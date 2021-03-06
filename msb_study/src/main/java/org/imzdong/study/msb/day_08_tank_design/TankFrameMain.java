package org.imzdong.study.msb.day_08_tank_design;

import org.imzdong.study.msb.day_08_tank_design.util.Audio;

public class TankFrameMain {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();

        new Thread(()->new Audio("audio/war1.wav").loop()).start();
        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
