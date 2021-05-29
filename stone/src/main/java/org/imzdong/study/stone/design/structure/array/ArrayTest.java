package org.imzdong.study.stone.design.structure.array;

/**
 * @description:
 * @author: Winter
 * @time: 2020年4月30日, 0030 18:19
 */
public class ArrayTest {

    public static void main(String[] args) {
        //intArray();
        strArray();
    }

    private static void strArray() {
        GenericArrayCustom<String> arrayCustom = new GenericArrayCustom<>(10);
        for(int num=0;num<12;num++){
            arrayCustom.add("dong:"+num);
        }
        System.out.println(arrayCustom.length());
        System.out.println(arrayCustom);
        System.out.println("--------------------------------");
        arrayCustom.insert(9,"dong:"+234);
        System.out.println(arrayCustom.length());
        System.out.println(arrayCustom);
        System.out.println("--------------------------------");
        System.out.println(arrayCustom.get(10));
        System.out.println("--------------------------------");
        arrayCustom.delete(10);
        System.out.println(arrayCustom.length());
        System.out.println(arrayCustom);
    }

    private static void intArray() {
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
