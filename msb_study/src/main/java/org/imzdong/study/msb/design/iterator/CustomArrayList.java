package org.imzdong.study.msb.design.iterator;

import java.util.Arrays;

/**
 * 自定义数组集合
 * @see java.util.ArrayList
 */
public class CustomArrayList<E> implements CustomCollection<E> {

    private Object [] elements;
    private int capacity;
    private int size = 0;

    public CustomArrayList() {
        this.elements = new Object[10];
        this.capacity = 10;
    }

    @Override
    public void add(E e) {
        if(size >= capacity){
            capacity = size*2;
            Object[] newElements = new Object[capacity];
            System.arraycopy(elements,0,newElements,0,elements.length);
            elements = newElements;
        }
        elements[size++] = e;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public CustomIterator<E> iterator() {
        return new ArrayListIterator<E>();
    }

    @Override
    public String toString() {
        return "CustomArrayList{" +
                "elements=" + Arrays.toString(elements) +
                ", capacity=" + capacity +
                '}';
    }

    private class ArrayListIterator<E> implements CustomIterator<E>{

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() {
            if(currentIndex < size){
                return (E)elements[currentIndex++];
            }
            return null;
        }
    }
}
