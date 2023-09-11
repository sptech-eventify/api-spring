package eventify.api_spring.dto.utils.objects;

import java.util.Objects;

public class ListaLigada {
    private Node head;

    public ListaLigada() {
        this.head = new Node(null);
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
            if ((Double) atual.getInfo().getNotaMediaAvaliacao() == valor) {
                return atual;
            }
            atual = atual.getNext();
        }

        return null;
    }
}
