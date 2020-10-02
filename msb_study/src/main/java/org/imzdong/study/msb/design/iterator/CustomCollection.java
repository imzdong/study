package org.imzdong.study.msb.design.iterator;

public interface CustomCollection<E> extends CustomIterable<E> {

    void add(E e);

    int size();
}
