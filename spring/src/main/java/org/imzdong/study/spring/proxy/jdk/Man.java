package org.imzdong.study.spring.proxy.jdk;

public class Man implements Person{
    @Override
    public String buy() {
        System.out.println("man buy car");
        return "Car";
    }

    @Override
    public void eat() {
        System.out.println("man eat meat");
    }
}
