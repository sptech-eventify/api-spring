package eventify.api_spring.dto.utils.objects;

import java.util.Objects;

import eventify.api_spring.dto.buffet.BuffetResumoDto;

public class HashTable {
    private ListaLigada tab[];

    public HashTable (int tamanho) {
        this.tab = new ListaLigada[tamanho];

        for (int i = 0; i < tamanho; i++) {
            this.tab[i] = new ListaLigada();
        }
    }

    // Dentro da funcao Hash, o valor retornado deverá ser o inteiro do valor passado como parâmetro
    public int funcaoHash(Double valor) {
        return valor.intValue();
    }

    public void insere(BuffetResumoDto valor) {
        this.tab[this.funcaoHash(valor.getNotaMediaAvaliacao())].insereNode(new Node(valor));
    }

    public boolean busca(int x) {
        Node resultado = this.tab[x];

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
