package org.imzdong.study.msb.design.iterator;

public class CustomLinkedList<E> implements CustomCollection<E> {

    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(E e) {
        Node node = new Node(e);
        if(head == null){
            head = node;
            tail = node;
        }else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public CustomIterator<E> iterator() {
        return new LinkedListIterator<E>();
    }

    private class Node{

        Object object;
        Node next;

        public Node(Object object) {
            this.object = object;
        }
    }

    private class LinkedListIterator<E> implements CustomIterator<E> {

        private Node currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() {
            Object object = null;
            if (currentNode != null) {
                object = currentNode.object;
                currentNode = currentNode.next;
            }
            return (E)object;
        }

    }
}
