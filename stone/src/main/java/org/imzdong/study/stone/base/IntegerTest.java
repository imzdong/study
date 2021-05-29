package org.imzdong.study.stone.base;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * 1、==判断两个对象的地址是否相等（基本类型比较的是值，引用类型比较的是内存地址）
 * equals默认比较地址，可以重写
 * 2、自动拆箱，装箱知识点；Integer：装箱（valueOf），拆箱（intValue）
 * 3、String str1 = "abc"; String str2 = "abc"; str1放入常量池，str2从常量池获取
 * @author zhoud
 * @since 2021/2/23 13:10
 */
public class IntegerTest {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(1);
        map.put("123", "333");
        map.put("4444", "333");
        map.get("123");
        int parseInt = Integer.parseInt("123");
        System.out.println(parseInt);
        Object nullObject = null;
        Hashtable<String, String> strTable = new Hashtable<>();
        strTable.put(null, "123");
        System.out.println(strTable);
    }

    private static void testEquals() {
        Integer a = 1;
        Integer b = 1;
        Integer c = 2;
        System.out.println(a==b);//true
        System.out.println(a.equals(b));//true
        System.out.println(c==(a+b));//ture

        Integer e = 129;
        Integer f = 129;
        System.out.println(e==f);
    }

    private static void testBx() {
        //System.out.println(127 == new Integer(127));
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;

        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(c==(a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));
        System.out.println(g.equals(a+h));
    }

    private static void testInter() {
        Integer i127 = new Integer(127);
        int a = 127;
        int b = 127;
        Integer i127_1 = new Integer(127);
        System.out.println(a == i127);//true
        System.out.println(a == b);//true
        System.out.println(i127 == b);//true
        System.out.println(i127 == i127_1);//false
        System.out.println(i127.equals(i127_1));//true
        System.out.println("-----------------------------");
        System.out.println(i127_1 == a);//true
        Integer i129 = new Integer(129);
        System.out.println(i129 == 129);//true
        Integer i1000 = new Integer(1000);
        System.out.println(i1000 == 1000);//true
        Integer i1000_1 = new Integer(1000);
        System.out.println(i1000.equals(i1000_1));//true
    }


}
