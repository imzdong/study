package org.imzdong.study.msb.day_08_design_tank;

import org.imzdong.study.msb.day_08_design_tank.collide.*;
import org.imzdong.study.msb.day_08_design_tank.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.factory.CustomStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.factory.DefaultStyleFactory;
import org.imzdong.study.msb.day_08_design_tank.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_design_tank.model.Dir;
import org.imzdong.study.msb.day_08_design_tank.model.Group;
import org.imzdong.study.msb.day_08_design_tank.model.Wall;
import org.imzdong.study.msb.day_08_design_tank.util.PropertyMgr;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModelFacade {

    public AbstractStyleFactory abstractStyleFactory = new DefaultStyleFactory();
    //public AbstractStyleFactory abstractStyleFactory = new CustomStyleFactory();
    CollateChain collateChain = new CollateChain();

    BaseTank tank = abstractStyleFactory.createTank(400,500, Dir.UP, this, Group.GOOD);
    public List<GameObject> gameObjects = new ArrayList<>();

    public GameModelFacade(){
        int initEnemyCount = PropertyMgr.getInt("initEnemyCount");
        int enemySpace = PropertyMgr.getInt("enemySpace");
        for (int i = 0; i < initEnemyCount; i++) {
            BaseTank enemy = abstractStyleFactory.createTank(i*enemySpace+50, 300, Dir.DOWN, this, Group.BAD);
            this.add(enemy);
        }
        add(new Wall(200,50,50,250));
        add(new Wall(700,400,400,30));
        add(new Wall(200,550,30,300));
        collateChain.add(new BulletCollision());
        collateChain.add(new TankCollision());
        collateChain.add(new BulletWallCollision());
        collateChain.add(new TankWallCollision());
    }

    public void add(GameObject gameObject){
        gameObjects.add(gameObject);
    }


    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.red);
        /*g.drawString("子弹数量："+bullets.size(),10,50);
        g.drawString("敌人数量："+enemyList.size(),10,70);
        g.drawString("boom数量："+booms.size(),10,90);*/
        g.setColor(color);
        tank.paint(g);
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }
        //射击敌人
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int y = i+1; y < gameObjects.size(); y++) {
                collateChain.collision(gameObjects.get(i), gameObjects.get(y));
            }
        }
    }

    public BaseTank createMainTank() {
        return tank;
    }

    public void remove(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }
}
