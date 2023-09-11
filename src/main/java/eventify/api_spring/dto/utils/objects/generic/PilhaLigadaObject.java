package com.paradigmas.generico;

public class PilhaLigadaObject<T> {
    private NodeObject<T> topo;

    public PilhaLigadaObject() {
        topo = null;
    }

    public void push(T valor){
        NodeObject<T> novo = new NodeObject<>(valor);
        novo.setNext(topo);
        topo = novo;
    }

    public boolean isEmpty(){
        return topo == null;
    }

    public T pop(){
        if(isEmpty()){
            throw new RuntimeException("Pilha vazia");
        }

        T valor = topo.getInfo();
        topo = topo.getNext();
        return valor;
    }

    public T peek(){
        if(isEmpty()){
            throw new RuntimeException("Pilha vazia");
        }

        return topo.getInfo();
    }

    public void exibe(){
        NodeObject<T> aux = topo;

        while(aux != null){
            System.out.println(aux.getInfo());
            aux = aux.getNext();
        }
    }
}