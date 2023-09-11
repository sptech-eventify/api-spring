package com.paradigmas.generico;


public class NodeObject<T> {
    private T info;
    private NodeObject<T> next;

    public NodeObject(T info) {
        this.info = info;
        this.next = null;
    }

    public T getInfo() {
        return this.info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public NodeObject<T> getNext() {
        return this.next;
    }

    public void setNext(NodeObject<T> next) {
        this.next = next;
    }
}
