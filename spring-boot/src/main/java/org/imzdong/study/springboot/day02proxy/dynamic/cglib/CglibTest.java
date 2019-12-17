package org.imzdong.study.springboot.day02proxy.dynamic.cglib;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月28日, 0028 14:43
 */
public class CglibTest {

    public static void main(String[] args) {
        RealPeople realPeople = (RealPeople) CglibProxy.getInstance(RealPeople.class);
        realPeople.buy();
    }
}
