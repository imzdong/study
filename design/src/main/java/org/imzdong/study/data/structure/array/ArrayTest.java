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
        ArrayCustom arrayCustom = new ArrayCustom(10);
        for(int num=0;num<12;num++){
            arrayCustom.add(num);
        }
        System.out.println(arrayCustom.length());
        System.out.println(arrayCustom);


        List<String> list = new ArrayList();
    }
}
