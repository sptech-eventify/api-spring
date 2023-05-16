package eventify.api_spring.api.assets;

import eventify.api_spring.domain.Buffet;

import java.util.List;

public class ListaBuffet {
    private Buffet[] vetor;
    private int nroElem;
    private int tamanhoMaximo = 0;


    public ListaBuffet(int tamanho) {
        nroElem = 0;
        vetor = (Buffet[]) new Object[tamanho];
        tamanhoMaximo = tamanho;
    }

    public void adiciona(Buffet valor) {
        if (nroElem >= tamanhoMaximo) {
            throw new IllegalArgumentException("Lista cheia");
        } else {
            vetor[nroElem++] = valor;
        }
    }

    public int tamanho(){
        return nroElem;
    }

    public void exibe() {
        for (int i = 0; i < nroElem; i++) {
            System.out.println(vetor[i]);
        }
    }

    public int busca(Buffet elemento) {
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i] == elemento) {
                return i;
            }
        }

        return -1;
    }

    public Buffet get(int indice) {
        if (indice <= nroElem) {
            return vetor[indice];
        }else{
            throw new IllegalArgumentException("Indice invalido");
        }
    }

    public boolean removePeloIndice(int indice) {
        if (indice >= nroElem) {
            return false;
        } else {
            boolean isRemovido = false;

            for (int i = 0; i < nroElem; i++) {
                if(isRemovido){
                    vetor[i-1] = vetor[i];
                }else{
                    if (i == indice) {
                        isRemovido = true;
                    }
                }
            }

            nroElem--;
            return true;
        }
    }

    public boolean removeElemento(Buffet elemento){
        return removePeloIndice(busca(elemento));
    }

    public boolean substitui(Buffet antigo, Buffet novo){
        boolean isSubstituido = false;

        for(int i = 0; i < nroElem; i++){
            if(vetor[i] == antigo){
                vetor[i] = novo;
                isSubstituido = true;
            }
        }

        return isSubstituido;
    }

    public int contaOcorrencias(Buffet valor){
        int ocorrencias = 0;

        for(int i = 0; i < nroElem; i++){
            if(vetor[i] == valor){
                ocorrencias++;
            }
        }

        return ocorrencias;
    }

    public boolean adicionaNoInicio(Buffet valor){
        if(nroElem == tamanhoMaximo){
            return false;
        }

        Buffet valorAuxiliar = vetor[0];
        for(int i = 0; i < nroElem; i++){
            if(i == 0){
                vetor[0] = valor;
            }else{
                Buffet valorAuxilio2 = vetor[i];
                vetor[i] = valorAuxiliar;
                valorAuxiliar = valorAuxilio2;
            }
        }

        return true;
    }


    // Método de ordenação QuickSort
    public void ordenaPorNome() {
        quicksort(0, nroElem - 1);
    }

    private void quicksort(int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = particiona(inicio, fim);
            quicksort(inicio, posicaoPivo - 1);
            quicksort(posicaoPivo + 1, fim);
        }
    }

    private int particiona(int inicio, int fim) {
        Buffet pivo = vetor[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (vetor[j].getNome().compareTo(pivo.getNome()) <= 0) {
                i++;
                Buffet temp = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = temp;
            }
        }
        Buffet temp = vetor[i + 1];
        vetor[i + 1] = vetor[fim];
        vetor[fim] = temp;
        return i + 1;
    }

    public void ordenarPorPreco() {
        boolean troca = true;
        int tamanho = nroElem;

        while (troca) {
            troca = false;
            for (int i = 0; i < tamanho - 1; i++) {
                if (vetor[i].getPrecoMedioDiaria() > vetor[i + 1].getPrecoMedioDiaria()) {
                    Buffet temp = vetor[i];
                    vetor[i] = vetor[i + 1];
                    vetor[i + 1] = temp;
                    troca = true;
                }
            }
            tamanho--;
        }
    }
}
