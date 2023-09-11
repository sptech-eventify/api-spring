package com.paradigmas.generico;

public class FilaLigadaObject<T> {
    private NodeObject<T> inicio;
    private NodeObject<T> fim;

    public FilaLigadaObject() {
        inicio = null;
        fim = null;
    }

    public boolean isEmpty(){
        return inicio == null;
    }

    public void insert(T valor){
        NodeObject<T> novo = new NodeObject<>(valor);
        if(isEmpty()){
            inicio = novo;
            fim = novo;
        } else {
            fim.setNext(novo);
            fim = novo;
        }
    }

    public T poll(){
        if(isEmpty()){
            throw new RuntimeException("Fila vazia");
        }

        T valor = inicio.getInfo();
        inicio = inicio.getNext();
        return valor;
    }

    public T peek(){
        if(isEmpty()){
            throw new RuntimeException("Fila vazia");
        }

        return inicio.getInfo();
    }

    public void exibe(){
        NodeObject<T> aux = inicio;

        while(aux != null){
            System.out.println(aux.getInfo());
            aux = aux.getNext();
        }
    }
}