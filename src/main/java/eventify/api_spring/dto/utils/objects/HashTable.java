package eventify.api_spring.dto.utils.objects;

import java.util.Objects;

public class HashTable {
    private ListaLigada tab[];

    public HashTable (int tamanho) {
        this.tab = new ListaLigada[tamanho];

        for (int i = 0; i < tamanho; i++) {
            this.tab[i] = new ListaLigada();
        }
    }

    public int funcaoHash(int valor) {
        return valor % this.tab.length;
    }

    public void insere(int valor) {
        this.tab[this.funcaoHash(valor)].insereNode(new Node(valor));
    }

    public boolean busca(int x) {
        Node resultado = this.tab[this.funcaoHash(x)].buscaNode(x);

        if (Objects.nonNull(resultado)) {
            return true;
        }

        return false;
    }

    public boolean remove (int x) {
        return this.tab[this.funcaoHash(x)].removeNode(x);
    }

    public void exibe() {
        for (int i = 0; i < this.tab.length; i++) {
            System.out.println("Posição " + i + ":");
            this.tab[i].exibe();
        }
    }
}
