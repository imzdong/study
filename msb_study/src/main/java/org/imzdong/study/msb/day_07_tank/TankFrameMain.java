package org.imzdong.study.msb.day_07_tank;

public class TankFrameMain {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        while (true){
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
