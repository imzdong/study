package org.imzdong.study.data.structure.array;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Winter
 * @time: 2020年4月30日, 0030 18:19
 */
public class ArrayTest {

    public static void main(String[] args) {
        int a = 10;
        a = 10 + (10>>1);
        System.out.println(a);
        ArrayCustom arrayCustom = new ArrayCustom(10);
        for(int num=0;num<12;num++){
            arrayCustom.add(num);
        }
        System.out.println(arrayCustom.length());
        System.out.println(arrayCustom);
        System.out.println("--------------------------------");
        arrayCustom.insert(9,234);
        System.out.println(arrayCustom.length());
        System.out.println(arrayCustom);
        System.out.println("--------------------------------");
        System.out.println(arrayCustom.get(10));
        System.out.println("--------------------------------");
        arrayCustom.delete(10);
        System.out.println(arrayCustom.length());
        System.out.println(arrayCustom);

    }
}
