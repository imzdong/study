package org.imzdong.study.springboot.day04single;

/**
 * @description:
 * @author: Winter
 * @time: 2019/12/11
 */
public class SingleMain {
    public static void main(String[] args) {
        for(int x=0;x<3;x++){
            new Thread(()->System.out.println());
        }
    }
}
