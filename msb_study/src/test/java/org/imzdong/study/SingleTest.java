package org.imzdong.study;

import org.imzdong.study.msb.design.single.SingleEnum;
import org.imzdong.study.msb.design.single.SingleHungry;
import org.imzdong.study.msb.design.single.SingleInnerClass;
import org.imzdong.study.msb.design.single.SingleLazy;
import org.junit.Test;

public class SingleTest {

    @Test
    public void singleHungry(){
        //new SingleHungry();
        for (int i = 0; i < 5; i++) {
            new Thread(()->System.out.println(Thread.currentThread().getName()+":"+SingleHungry.getInstance())).start();
        }
    }

    @Test
    public void singleLazy(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->System.out.println(Thread.currentThread().getName()+":"+ SingleLazy.getInstance())).start();
        }
    }

    @Test
    public void singleInnerClass(){
        for (int i = 0; i < 5; i++) {
            new Thread(()->System.out.println(Thread.currentThread().getName()+":"+ SingleInnerClass.getInstance())).start();
        }
    }

    @Test
    public void singleEnumClass(){
        /*for (int i = 0; i < 5; i++) {
            new Thread(()->System.out.println(Thread.currentThread().getName()+":"+ SingleEnum.INSTANCE)).start();
        }*/
        SingleEnum instance = SingleEnum.INSTANCE;
        instance.single();
    }
}
