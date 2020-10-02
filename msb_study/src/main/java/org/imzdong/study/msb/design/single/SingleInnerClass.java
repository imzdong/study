package org.imzdong.study.msb.design.single;

/**
 * 完美版 单例
 */
public class SingleInnerClass {

    private SingleInnerClass(){

    }
    private final static class SingleInnerClassHodler{
       private static SingleInnerClass singleInnerClass = new SingleInnerClass();
    }

    public static SingleInnerClass getInstance(){
        return SingleInnerClassHodler.singleInnerClass;
    }

    public void innerClass(){
        System.out.println("I'm innerClass!!!");
    }

}
