package org.imzdong.study.msb.design.strategy;

public class CatAgeStrategy implements Strategy<Cat> {
    @Override
    public int compare(Cat o1, Cat o2) {
        if(o1.getAge()>o2.getAge())return -1;
        if(o1.getAge()==o2.getAge())return 0;
        if(o1.getAge()<o2.getAge())return 1;
        return 0;
    }
}
