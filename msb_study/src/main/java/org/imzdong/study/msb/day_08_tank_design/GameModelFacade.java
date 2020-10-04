package org.imzdong.study.msb.day_08_tank_design;

import org.imzdong.study.msb.day_08_tank_design.collide.*;
import org.imzdong.study.msb.day_08_tank_design.factory.AbstractStyleFactory;
import org.imzdong.study.msb.day_08_tank_design.factory.factory.DefaultStyleFactory;
import org.imzdong.study.msb.day_08_tank_design.factory.product.BaseTank;
import org.imzdong.study.msb.day_08_tank_design.model.Dir;
import org.imzdong.study.msb.day_08_tank_design.model.Group;
import org.imzdong.study.msb.day_08_tank_design.model.Wall;
import org.imzdong.study.msb.day_08_tank_design.util.PropertyMgr;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameModelFacade {

    private final static GameModelFacade gm = new GameModelFacade();
    public final static AbstractStyleFactory abstractStyleFactory = new DefaultStyleFactory();
    //public final static AbstractStyleFactory abstractStyleFactory = new CustomStyleFactory();
    public final static CollateChain collateChain = new CollateChain();

    private static BaseTank tank;// = abstractStyleFactory.createTank(400,500, Dir.UP, Group.GOOD);
    public List<GameObject> gameObjects = new ArrayList<>();

    private GameModelFacade(){}

    static {
        gm.init();
    }

    private void init(){
        tank = abstractStyleFactory.createTank(400,500, Dir.UP, Group.GOOD);
        int initEnemyCount = PropertyMgr.getInt("initEnemyCount");
        int enemySpace = PropertyMgr.getInt("enemySpace");
        for (int i = 0; i < initEnemyCount; i++) {
            abstractStyleFactory.createTank(i*enemySpace+50, 300, Dir.DOWN , Group.BAD);
        }
        new Wall(200,50,20,250);
        new Wall(700,400,400,30);
        new Wall(200,550,30,300);
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

    public static GameModelFacade getGm() {
        return gm;
    }

    public void save() {
        String clazzPath = this.getClass().getResource("/").getPath();
        System.out.println(clazzPath);
        String path = clazzPath+"/save.data";
        File file = new File(path);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))){
            outputStream.writeObject(tank);
            outputStream.writeObject(gameObjects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        String clazzPath = this.getClass().getResource("/").getPath();
        System.out.println(clazzPath);
        String path = clazzPath+"/save.data";
        File file = new File(path);
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))){
            tank = (BaseTank) inputStream.readObject();
            gameObjects = (List) inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
