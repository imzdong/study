package org.imzdong.study.msb.day_09_tank_net;

import org.imzdong.study.msb.day_09_tank_net.model.*;
import org.imzdong.study.msb.day_09_tank_net.net.Client;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {

    private int frameWith = 1000;
    private int frameHeight = 680;
    private Random random = new Random();
    private final static TankFrame tankFrame = new TankFrame();
    public static TankFrame getInstance(){
        return tankFrame;
    }

    private Tank tank ;//= new Tank(40+random.nextInt(500),50+random.nextInt(400), Dir.UP, this, Group.GOOD, UUID.randomUUID());
    public List<Bullet> bullets = new ArrayList<>();
    public Map<String,Tank> enemyList = new HashMap<>();
    public List<Boom> booms = new ArrayList<>();

    public Tank getTank() {
        return tank;
    }

    private TankFrame(){
        setTitle("坦克大战");
        setVisible(true);
        setSize(frameWith,frameHeight);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Client.getInstance().getChannel().close();
                System.exit(0);
            }
        });
        addKeyListener(new MyKeyListener());
        tank = new Tank(40+random.nextInt(500),50+random.nextInt(400), Dir.UP, this, Group.GOOD, UUID.randomUUID());
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
        g.drawString("boom数量："+booms.size(),10,90);
        g.setColor(color);
        tank.paint(g);
        //enemyList.put(tank.getUuid().toString(), tank);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        //添加敌人tank
        enemyList.values().forEach(t->t.paint(g));

        for (int i = 0; i < booms.size(); i++) {
            booms.get(i).paint(g);
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
