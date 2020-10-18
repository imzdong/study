package org.imzdong.study.msb.design.proxy.statical;

import org.imzdong.study.msb.design.proxy.Person;

public class TimeManProxy implements Person {

    private Person person;

    public TimeManProxy(Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        System.out.println("run start time ");
        person.run();
        System.out.println("run after time ");
    }

    @Override
    public int age() {
        return 0;
    }
}
