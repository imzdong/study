package org.imzdong.study.msb.design.proxy.statical;

import org.imzdong.study.msb.design.proxy.Person;

public class LogManProxy implements Person {

    private Person person;

    public LogManProxy(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        System.out.println("man start in beijing");
        person.run();
        System.out.println("man leave beijing");
    }

    @Override
    public int age() {
        return 0;
    }
}
