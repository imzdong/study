package org.imzdong.study.stone.base;

/**
 * java参数传递，值传递（方法得到的是一个值的copy）
 * 一个方法不能修改一个基本数据类型的参数（即数值型或布尔型）。
 * 一个方法可以改变一个对象参数的状态。
 * 一个方法不能让对象参数引用一个新的对象。
 *
 * @author zhoud
 * @since 2021/2/25 7:52
 */
public class PassByValue {

    public static void main(String[] args) {
        int x = 10;
        swapBase(x);
        System.out.println(x);
        Person xx = new Person("小小");
        Person yy = new Person("大大");
        swapRef(xx, yy);
        System.out.println(xx.name);
        System.out.println(yy.name);
    }

    private static void swapBase(int x){
        x = 1000;
    }

    private static void swapRef(Person x, Person y){
        System.out.println(x);
        System.out.println(y);
        Person temp = x;
        x = y;
        y = temp;
        System.out.println(x.name);
        System.out.println(y.name);
    }

    private static class Person{
        String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
