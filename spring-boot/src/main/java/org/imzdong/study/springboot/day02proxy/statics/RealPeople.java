package org.imzdong.study.springboot.day02proxy.statics;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月27日, 0027 15:03
 */
public class RealPeople implements ProxyInterFace{

    @Override
    public void buy() {
        System.out.println("RealPeople真正的购买人：开始买东西，付钱");
    }
}
