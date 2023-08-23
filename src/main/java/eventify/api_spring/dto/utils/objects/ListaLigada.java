package eventify.api_spring.dto.utils.objects;

import java.util.Objects;

public class ListaLigada {
    private Node head;

    public ListaLigada() {
        this.head = new Node(0);
    }

    public Node getHead() {
        return this.head;
    }

    public void insereNode(Node node) {
        Node novo = new Node(node.getInfo());

        novo.setNext(this.head.getNext());
        this.head.setNext(novo);
    }

    public void exibe(){
        Node atual = this.head.getNext();

        while (Objects.nonNull(atual)) {
            System.out.println(atual.getInfo());
            atual = atual.getNext();
        }
    }

    public Node buscaNode(int valor) {
        Node atual = this.head.getNext();

        while (Objects.nonNull(atual)) {
            if (atual.getInfo() == valor) {
                return atual;
            }
            atual = atual.getNext();
        }

        return null;
    }

    public boolean removeNode (int valor) {
        Node antigo = this.head;
        Node atual = this.head.getNext();

        while (Objects.nonNull(atual)) {
            if (atual.getInfo() == valor) {
                antigo.setNext(atual.getNext());
                return true;
            }
            antigo = atual;
            atual = atual.getNext();
        }

        return false;
    }

    public int getTamanho () {
        Node atual = this.head.getNext();

        int tamanho = 0;

        while (Objects.nonNull(atual)) {
            tamanho++;
            atual = atual.getNext();
        }

        return tamanho;
    }

    public void concatena(ListaLigada lista) {
        Node atual = this.head.getNext();
        Node antigo = this.head;
        Node atual2 = lista.getHead().getNext();

        while (Objects.nonNull(atual)) {
            antigo = atual;
            atual = atual.getNext();
        }

        while (atual2 != null) {
            Node novo = new Node(atual2.getInfo());
            antigo.setNext(novo);
            antigo = novo;
            atual2 = atual2.getNext();
        }
    }

    public boolean compara (ListaLigada lista) {
        boolean iguais = true;

        Node atual = this.head.getNext();
        Node atual2 = lista.getHead().getNext();

        while (Objects.nonNull(atual) && Objects.nonNull(atual2)) {
            if (atual.getInfo() != atual2.getInfo()) {
                iguais = false;
                break;
            }
            atual = atual.getNext();
            atual2 = atual2.getNext();
        }

        if (Objects.nonNull(atual) || Objects.nonNull(atual2)) {
            iguais = false;
        }

        return iguais;
    }

    public void inverte() {
        Node atual = this.head.getNext();
        Node antigo = null;
        Node proximo = null;

        while (Objects.nonNull(atual)) {
            proximo = atual.getNext();
            atual.setNext(antigo);
            antigo = atual;
            atual = proximo;
        }

        this.head.setNext(antigo);
    }

    public void exibeRecursivo (Node node) {
        if (Objects.nonNull(node)) {
            System.out.println(node.getInfo());
            exibeRecursivo(node.getNext());
        }
    }

    public Node buscaNodeRecursivo (Node node, int valor) {
        if (Objects.isNull(node)) {
            return null;
        }

        if (node.getInfo() == valor) {
            return node;
        }

        return buscaNodeRecursivo(node.getNext(), valor);
    }

    public boolean removeNodeRecursivo (Node node, int valor) {
        if (Objects.isNull(node)) {
            return false;
        }

        if (node.getInfo() == valor) {
            return true;
        }

        return removeNodeRecursivo(node.getNext(), valor);
    }

    public int getTamanhoRecursivo (Node node) {
        if (Objects.isNull(node)) {
            return 0;
        }

        return 1 + getTamanhoRecursivo(node.getNext());
    }

    public Node getPrimeiro () {
        return this.head.getNext();
    }

    public void inserirAposPrimeiroImpar (int valor) {
        Node atual = this.head.getNext();
        Node antigo = this.head;

        while (Objects.nonNull(atual)) {
            if (atual.getInfo() % 2 != 0) {
                Node novo = new Node(valor);
                novo.setNext(atual.getNext());
                atual.setNext(novo);
                break;
            }
            antigo = atual;
            atual = atual.getNext();
        }

        if (Objects.isNull(atual)) {
            Node novo = new Node(valor);
            antigo.setNext(novo);
        }
    }

    public int getElemento(int indice) {
        Node atual = this.head.getNext();

        int i = 0;

        while (Objects.nonNull(atual)) {
            if (i == indice) {
                return atual.getInfo();
            }
            atual = atual.getNext();
            i++;
        }

        return -1;
    }

    public void removeElemento(int indice) {
        Node atual = this.head.getNext();
        Node antigo = this.head;

        int i = 0;

        while (Objects.nonNull(atual)) {
            if (i == indice) {
                antigo.setNext(atual.getNext());
                break;
            }
            antigo = atual;
            atual = atual.getNext();
            i++;
        }
    }

    public boolean removeOcorrencias(int valor) {
        Node atual = this.head.getNext();
        Node antigo = this.head;

        boolean removeu = false;

        while (Objects.nonNull(atual)) {
            if (atual.getInfo() == valor) {
                antigo.setNext(atual.getNext());
                removeu = true;
            } else {
                antigo = atual;
            }
            atual = atual.getNext();
        }

        return removeu;
    }

    public void arrayToList(int[] vetor) {
        if (Objects.nonNull(this.head.getNext())) {
            System.out.println("Operação inválida");
            return;
        }

        for (int i = 0; i < vetor.length; i++) {
            Node novo = new Node(vetor[i]);
            novo.setNext(this.head.getNext());
            this.head.setNext(novo);
        }
    }
}
