package com.paradigmas.generico;

import java.util.Objects;

public class ListaLigadaObject<T> {
    private NodeObject<T> head;

    public ListaLigadaObject() {
        this.head = new NodeObject<>(null);
    }

    public NodeObject<T> getHead() {
        if (Objects.nonNull(this.head)) {
            return this.head;
        } else {
            return null;
        }
    }

    public void insereNode(NodeObject<T> node) {
        NodeObject<T> novo = new NodeObject<>(node.getInfo());

        novo.setNext(this.head.getNext());
        this.head.setNext(novo);
    }

    public void exibe(){
        NodeObject<T> atual = this.head.getNext();

        while (atual != null) {
            System.out.println(atual.getInfo());
            atual = atual.getNext();
        }
    }

    public NodeObject<T> buscaNode(T valor) {
        NodeObject<T> atual = this.head.getNext();

        while (atual != null) {
            if (atual.getInfo().equals(valor)) {
                return atual;
            }

            atual = atual.getNext();
        }

        return null;
    }

    public boolean removeNode(T valor) {
        NodeObject<T> antigo = this.head;
        NodeObject<T> atual = this.head.getNext();

        while (atual != null) {
            if (atual.getInfo().equals(valor)) {
                antigo.setNext(atual.getNext());
                return true;
            }
            antigo = atual;
            atual = atual.getNext();
        }

        return false;
    }

    public int getTamanho() {
        NodeObject<T> atual = this.head.getNext();

        int tamanho = 0;

        while (atual != null) {
            tamanho++;
            atual = atual.getNext();
        }

        return tamanho;
    }

    public void concatena() {
        NodeObject atual = this.head.getNext();

        while (Objects.nonNull(atual)) {
            NodeObject novo = new NodeObject(atual.getInfo());
            novo.setNext(this.head.getNext());
            this.head.setNext(novo);
            atual = atual.getNext();
        }
    }

    public boolean compara(ListaLigadaObject<T> lista) {
        NodeObject<T> atual = this.head.getNext();
        NodeObject<T> atual2 = lista.getHead().getNext();

        while (atual != null && atual2 != null) {
            if (!atual.getInfo().equals(atual2.getInfo())) {
                return false;
            }
            atual = atual.getNext();
            atual2 = atual2.getNext();
        }

        return atual == null && atual2 == null;
    }

    public void inverte() {
        NodeObject<T> atual = this.head.getNext();
        NodeObject<T> antigo = null;
        NodeObject<T> proximo = null;

        while (atual != null) {
            proximo = atual.getNext();
            atual.setNext(antigo);
            antigo = atual;
            atual = proximo;
        }

        this.head.setNext(antigo);
    }

    public void exibeRecursivo(NodeObject<T> node) {
        if (node != null) {
            System.out.println(node.getInfo());
            exibeRecursivo(node.getNext());
        }
    }

    public NodeObject<T> buscaNodeRecursivo(NodeObject<T> node, T valor) {
        if (node == null) {
            return null;
        }

        if (node.getInfo().equals(valor)) {
            return node;
        }

        return buscaNodeRecursivo(node.getNext(), valor);
    }

    public boolean removeNodeRecursivo(NodeObject<T> node, T valor) {
        if (Objects.isNull(node.getInfo())) {
            return false;
        }

        if (node.getInfo().equals(valor)) {
            return true;
        }

        return removeNodeRecursivo(node.getNext(), valor);
    }

    public int getTamanhoRecursivo(NodeObject<T> node) {
        if (node == null) {
            return 0;
        }

        return 1 + getTamanhoRecursivo(node.getNext());
    }

    public NodeObject<T> getPrimeiro() {
        return this.head.getNext();
    }

    public T getElemento(int indice) {
        NodeObject<T> atual = this.head.getNext();

        int i = 0;

        while (atual != null) {
            if (i == indice) {
                return atual.getInfo();
            }
            atual = atual.getNext();
            i++;
        }

        return null;
    }

    public void removeElemento(int indice) {
        NodeObject<T> atual = this.head.getNext();
        NodeObject<T> antigo = this.head;

        int i = 0;

        while (atual != null) {
            if (i == indice) {
                antigo.setNext(atual.getNext());
                break;
            }
            antigo = atual;
            atual = atual.getNext();
            i++;
        }
    }

    public boolean removeOcorrencias(T valor) {
        NodeObject<T> atual = this.head.getNext();
        NodeObject<T> antigo = this.head;

        boolean removeu = false;

        while (atual != null) {
            if (atual.getInfo().equals(valor)) {
                antigo.setNext(atual.getNext());
                removeu = true;
            } else {
                antigo = atual;
            }
            atual = atual.getNext();
        }

        return removeu;
    }

    public void arrayToList(T[] vetor) {
        if (this.head.getNext() != null) {
            System.out.println("Operação inválida");
            return;
        }

        for (T valor : vetor) {
            NodeObject<T> novo = new NodeObject<>(valor);
            novo.setNext(this.head.getNext());
            this.head.setNext(novo);
        }
    }
}