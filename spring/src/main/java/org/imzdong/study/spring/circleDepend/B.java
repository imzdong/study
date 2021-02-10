package org.imzdong.study.spring.circleDepend;

public class B {

    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enhanceMethod(){
        System.out.println("B:enhanceMethod");
    }

}
