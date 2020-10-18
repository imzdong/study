package org.imzdong.study.msb.design.single;

/**
 * 饿汉单例模式，工作用就ok了
 */
public class SingleHungry {

    private static SingleHungry singleHungry = new SingleHungry();

    private SingleHungry(){}

    public static SingleHungry getInstance(){
        return singleHungry;
    }

    public void hungry(){
        System.out.println("I'm hungry!!!");
    }
}
