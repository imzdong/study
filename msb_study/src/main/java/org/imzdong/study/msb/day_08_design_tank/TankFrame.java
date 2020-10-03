package org.imzdong.study.msb.day_08_design_tank;

import org.imzdong.study.msb.day_08_design_tank.constant.Constant;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {

    GameModelFacade gm = GameModelFacade.getGm();

    public TankFrame(){
        setTitle("坦克大战");
        setVisible(true);
        setSize(Constant.frameWith,Constant.frameHeight);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new MyKeyListener());
    }

    Image image = null;
    /**
     * 解决前端闪烁问题，原理，在内存构建画布，再给屏幕展示
     * @param g
     */
    @Override
    public void update(Graphics g) {
        if(image == null) {
            image = createImage(Constant.frameWith, Constant.frameHeight);
        }
        Graphics graphics = image.getGraphics();
        Color color = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, Constant.frameWith, Constant.frameHeight);
        graphics.setColor(color);
        paint(graphics);
        g.drawImage(image,0,0,null);
    }

    /**
     * 画布
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    /**
     * 键盘监听事件
     */
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
                case KeyEvent.VK_CONTROL:
                    gm.createMainTank().fire();
                    break;
                case KeyEvent.VK_S:
                    gm.save();
                case KeyEvent.VK_L:
                    gm.load();
                    break;
                default:
                    break;
            }
            setMainFrame();
        }

        private void setMainFrame() {
            BaseTank tank = gm.createMainTank();
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
