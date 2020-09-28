package org.imzdong.study.msb.day_07_tank;

import org.imzdong.study.msb.day_07_tank.model.Dir;
import org.imzdong.study.msb.day_07_tank.model.Tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

    Tank tank = new Tank(200,200);

    public TankFrame(){
        setTitle("Tank War!!!");
        setVisible(true);
        setSize(650,500);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new MyKeyListener());
    }

    @Override
    public void paint(Graphics g) {
        //System.out.println("paint");
        tank.paint(g);
    }
    class MyKeyListener extends KeyAdapter{
        boolean bu = false;
        boolean bd = false;
        boolean bl = false;
        boolean br = false;
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode){
                case KeyEvent.VK_UP:
                    bu = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    break;
                case KeyEvent.VK_LEFT:
                    bl = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    break;
                default:
                    break;
            }
            setMainFrame();
        }

        private void setMainFrame() {
            if(!bu&&!bd&!bl&!br){
                tank.setMoving(false);
            }else {
                if (bu) {
                    tank.setDir(Dir.UP);
                }
                if (bd) {
                    tank.setDir(Dir.DOWN);
                }
                if (bl) {
                    tank.setDir(Dir.LEFT);
                }
                if (br) {
                    tank.setDir(Dir.RIGHT);
                }
                tank.setMoving(true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode){
                case KeyEvent.VK_UP:
                    bu = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    break;
                case KeyEvent.VK_LEFT:
                    bl = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    break;
                default:
                    break;
            }
            setMainFrame();
        }
    }
}
