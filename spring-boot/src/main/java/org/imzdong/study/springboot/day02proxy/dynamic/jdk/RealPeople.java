package org.imzdong.study.springboot.day02proxy.dynamic.jdk;

/**
 * @description:
 * @author: Winter
 * @time: 2019年11月27日, 0027 19:42
 */
public class RealPeople implements PeopleInterface{

    @Override
    public void buy(){
        System.out.println("buy realPeople dynamic");
    }
}
