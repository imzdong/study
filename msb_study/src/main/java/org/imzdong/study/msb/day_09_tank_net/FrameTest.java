package org.imzdong.study.msb.day_09_tank_net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameTest {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setResizable(false);
        frame.setTitle("tank war");
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
