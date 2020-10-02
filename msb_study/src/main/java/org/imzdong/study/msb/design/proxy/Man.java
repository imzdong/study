package org.imzdong.study.msb.design.proxy;

public class Man implements Person, Movable{
    @Override
    public void run() {
        System.out.println("run Man!!!");
    }

    @Override
    public int age() {
        System.out.println("I'm age");
        return 10;
    }

    @Override
    public void fly() {
        System.out.println("sky is my sky");
    }
}
