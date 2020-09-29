package org.imzdong.study.msb.desgin.strategy;

public class CatNameStrategy implements Strategy<Cat>{
    @Override
    public int compare(Cat o1, Cat o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
