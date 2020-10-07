package org.imzdong.study.msb.day_10_juc.lock;

import java.util.concurrent.Phaser;

public class PhaserDemo {

    public static void main(String[] args) {
        Phaser phaser = new MyPhaser();
        phaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("kk"+i, phaser)).start();
        }
        new Thread(new Person("man", phaser)).start();
        new Thread(new Person("woman", phaser)).start();
    }
}
class MyPhaser extends Phaser{

    /**
     * 定义阶段执行
     * @param phase
     * @param registeredParties
     * @return
     */
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase){
            case 0:
                System.out.println("到齐了:"+registeredParties);
                return false;
            case 1:
                System.out.println("吃饭了:"+registeredParties);
                return false;
            case 2:
                System.out.println("离开了:"+registeredParties);
                return false;
            case 3:
                System.out.println("睡觉了:"+registeredParties);
                return false;
            default:
                return true;
        }
    }
}
class Person implements Runnable {
    String name;
    Phaser phaser;

    public Person(String name, Phaser phaser) {
        this.name = name;
        this.phaser = phaser;
    }

    public void arrive() {
        System.out.printf("%s arrive\n", name);
        phaser.arriveAndAwaitAdvance();
    }

    public void eat() {
        System.out.printf("%s eat!\n", name);
        phaser.arriveAndAwaitAdvance();
    }

    public void leave() {
        System.out.printf("%s leave\n", name);
        phaser.arriveAndAwaitAdvance();
    }

    private void hug() {
        if(name.equals("man") || name.equals("woman")) {
            System.out.printf("%s hug\n", name);
            phaser.arriveAndAwaitAdvance();
        } else {
            phaser.arriveAndDeregister();
        }
    }

    @Override
    public void run() {
        arrive();
        eat();
        leave();
        hug();
    }
}
