package org.imzdong.study.msb.day_08_design_tank;

import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.factory.CustomStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBoom;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseBullet;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.model.Group;
import org.imzdong.study.msb.day_08_design_tank.util.PropertyMgr;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModelFacade {

    //public AbstractStyleFactory abstractStyleFactory = new DefaultStyleFactory();
    public AbstractStyleFactory abstractStyleFactory = new CustomStyleFactory();

    BaseTank tank = abstractStyleFactory.createTank(400,500, Dir.UP, this, Group.GOOD);
    public java.util.List<BaseBullet> bullets = new ArrayList<>();
    public java.util.List<BaseTank> enemyList = new ArrayList<>();
    public List<BaseBoom> booms = new ArrayList<>();

    public GameModelFacade(){
        int initEnemyCount = PropertyMgr.getInt("initEnemyCount");
        int enemySpace = PropertyMgr.getInt("enemySpace");
        for (int i = 0; i < initEnemyCount; i++) {
            BaseTank enemy = abstractStyleFactory.createTank(i*enemySpace+50, 300, Dir.DOWN, this, Group.BAD);
            this.enemyList.add(enemy);
        }
    }


    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.red);
        g.drawString("子弹数量："+bullets.size(),10,50);
        g.drawString("敌人数量："+enemyList.size(),10,70);
        g.drawString("boom数量："+booms.size(),10,90);
        g.setColor(color);
        tank.paint(g);
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }
        //添加敌人tank
        for (int i = 0; i < enemyList.size(); i++) {
            enemyList.get(i).paint(g);
        }
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

    public BaseTank createMainTank() {
        return tank;
    }
}
