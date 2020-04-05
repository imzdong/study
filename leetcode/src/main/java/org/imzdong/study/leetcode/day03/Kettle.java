package org.imzdong.study.leetcode.day03;

/**
 * @description:
 * @author: Winter
 * @time: 2020/3/21
 */
public class Kettle {

    public static void main(String[] args) {
        System.out.println(getA(5));
        //System.out.println(canMeasureWater(2,6,3));
    }

    private static String getA(int a){
        switch (a){
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            default:
                break;
        }
        return null;
    }

    public static boolean canMeasureWater(int x, int y, int z) {
        //首先处理几种特殊情况
        if (z == 0 || x + y == z){
            return true;
        } else if (x + y < z){
            return false;
        }
        //确保x < y
        if (x > y){
            int tempVal = x;
            x = y;
            y = tempVal;
        }
        if (x == 0){
            return y == z;
        }
        //寻找最大公约数,辗转相除法
        int val = x;
        while (y % x != 0){
            val = y % x;
            y = x;
            x = val;
        }
        return z % val == 0;
    }
}
