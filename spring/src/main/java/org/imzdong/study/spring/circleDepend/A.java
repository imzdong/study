package org.imzdong.study.spring.circleDepend;

public class A {

    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enhanceMethod(){
        System.out.println("A:enhanceMethod");
    }

}
