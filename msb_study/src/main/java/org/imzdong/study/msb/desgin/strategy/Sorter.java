package org.imzdong.study.msb.desgin.strategy;

public class Sorter<T> {

    public void compareTwo(T[] sorts,Strategy<T> strategy){
        T sort0 = sorts[0];
        T sort1 = sorts[1];
        T t0 = strategy.compare(sort0, sort1) >= 0 ? sort0 : sort1;
        sorts[0] = t0;
        if(!t0.equals(sort0)){
            sorts[1] = sort0;
        }
    }
}
