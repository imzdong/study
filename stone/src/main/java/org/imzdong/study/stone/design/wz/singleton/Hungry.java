package org.imzdong.study.stone.design.wz.singleton;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/11
 */
public class Hungry {

    private Hungry(){
    }

    private Hungry hungry = new Hungry();

    public Hungry getInstance(){
        return hungry;
    }
}
