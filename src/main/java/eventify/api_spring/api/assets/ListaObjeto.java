package eventify.api_spring.api.assets;

public class ListaObjeto<T> {
    private T[] vetor;
    private int nroElem;
    private int tamanhoMaximo = 0;


    public ListaObjeto(int tamanho) {
        nroElem = 0;
        vetor = (T[]) new Object[tamanho];
        tamanhoMaximo = tamanho;
    }

    public void adiciona(T valor) {
        if (nroElem >= tamanhoMaximo) {
            System.out.println("Lista cheia!");
        } else {
            vetor[nroElem++] = valor;
        }
    }

    public void exibe() {
        for (int i = 0; i < nroElem; i++) {
            System.out.println(vetor[i]);
        }
    }

    public int busca(T elemento) {
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i] == elemento) {
                return i;
            }
        }

        return -1;
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

    public boolean removeElemento(T elemento){
        return removePeloIndice(busca(elemento));
    }

    public boolean substitui(T antigo, T novo){
        boolean isSubstituido = false;

        if(vetor instanceof String[]){
            for(int i = 0; i < nroElem; i++){
                if(vetor[i].equals(antigo)){
                    vetor[i] = novo;
                    isSubstituido = true;
                }
            }
        }else{
            for(int i = 0; i < nroElem; i++){
                if(vetor[i] == antigo){
                    vetor[i] = novo;
                    isSubstituido = true;
                }
            }

        }

        return isSubstituido;
    }

    public int contaOcorrencias(T valor){
        int ocorrencias = 0;

        if(vetor instanceof String[]){
            for(int i = 0; i < nroElem; i++){
                if(vetor[i].equals(valor)){
                    ocorrencias++;
                }
            }
        }else{
            for(int i = 0; i < nroElem; i++){
                if(vetor[i] == valor){
                    ocorrencias++;
                }
            }
        }

        return ocorrencias;
    }

    public boolean adicionaNoInicio(T valor){
        if(nroElem == tamanhoMaximo){
            System.out.println("Lista cheia!");
            return false;
        }

        T valorAuxiliar = vetor[0];
        for(int i = 0; i < nroElem; i++){
            if(i == 0){
                vetor[0] = valor;
            }else{
                T valorAuxilio2 = vetor[i];
                vetor[i] = valorAuxiliar;
                valorAuxiliar = valorAuxilio2;
            }
        }

        return true;
    }
}
