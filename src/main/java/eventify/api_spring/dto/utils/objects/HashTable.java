package eventify.api_spring.dto.utils.objects;

import java.util.ArrayList;
import java.util.List;

import eventify.api_spring.dto.buffet.BuffetConsultaDto;

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

    public void insere(BuffetConsultaDto valor) {
        int indice = this.funcaoHash(valor.getNotaMediaAvaliacao());
        this.tab[indice].insereNode(new Node(valor));
    }

    public List<BuffetConsultaDto> busca(int indice) {
        List<BuffetConsultaDto> buffets = new ArrayList<>();

        Node atual = this.tab[indice].getHead().getNext();

        while (atual != null) {
            buffets.add(atual.getInfo());
            atual = atual.getNext();
        }

        return buffets;
    }
}
