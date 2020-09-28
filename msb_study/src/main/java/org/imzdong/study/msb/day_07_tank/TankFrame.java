package org.imzdong.study.msb.day_07_tank;

import org.imzdong.study.msb.day_07_tank.model.Bullet;
import org.imzdong.study.msb.day_07_tank.model.Dir;
import org.imzdong.study.msb.day_07_tank.model.Group;
import org.imzdong.study.msb.day_07_tank.model.Tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame {

    private int frameWith = 650;
    private int frameHeight = 500;

    Tank tank = new Tank(200,400, Dir.UP, this, Group.GOOD);
    public List<Bullet> bullets = new ArrayList<>();
    public List<Tank> enemyList = new ArrayList<>();

    public TankFrame(){
        setTitle("坦克大战");
        setVisible(true);
        setSize(frameWith,frameHeight);
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
            image = createImage(frameWith, frameHeight);
        }
        Graphics graphics = image.getGraphics();
        Color color = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, frameWith, frameHeight);
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
        Color color = g.getColor();
        g.setColor(Color.red);
        g.drawString("子弹数量："+bullets.size(),10,50);
        g.drawString("敌人数量："+enemyList.size(),10,70);
        g.setColor(color);
        tank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        //添加敌人tank
        for (int i = 0; i < enemyList.size(); i++) {
            enemyList.get(i).paint(g);
        }
        //射击敌人
        for (int i = 0; i < bullets.size(); i++) {
            for (int y = 0; y < enemyList.size(); y++) {
                bullets.get(i).collision(enemyList.get(y));
            }
        }
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
                    tank.fire();
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
