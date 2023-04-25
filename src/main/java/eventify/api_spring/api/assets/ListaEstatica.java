package eventify.api_spring.api.assets;

public class ListaEstatica {
    private int[] vetor;
    private int nroElem;

    public int[] getVetor() {
        return vetor;
    }

    public void setVetor(int[] vetor) {
        this.vetor = vetor;
    }

    public int getNroElem() {
        return nroElem;
    }

    public void setNroElem(int nroElem) {
        this.nroElem = nroElem;
    }

    public ListaEstatica(int tamanho) {
        nroElem = 0;
        vetor = new int[tamanho];
    }

    public void adiciona(int valor) {
        if (nroElem == vetor.length) {
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

    public int busca(int elemento) {
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

    public boolean removeElemento(int elemento){
        boolean isRemovido = false;

        for(int i = 0; i < nroElem; i++){
            if(elemento == vetor[i] && !(isRemovido)){
                isRemovido = true;
            }else if(isRemovido){
                vetor[i-1] = vetor[i];
            }
        }

        nroElem--;
        return isRemovido;
    }

    public boolean removeExistenciaElemento(int elemento){
        int count = 0;
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] == elemento) {
                count++;
            }
        }

        int[] novoVetor = new int[vetor.length - count];
        nroElem -= count;
        int j = 0;
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] != elemento) {
                novoVetor[j] = vetor[i];
                j++;
            }
        }

        vetor = null;
        vetor = novoVetor;
        return true;
    }

    public boolean substitui(int antigo, int novo){
        boolean isSubstituido = false;
        for(int i = 0; i < nroElem; i++){
            if(vetor[i] == antigo){
                vetor[i] = novo;
                isSubstituido = true;
            }
        }

        return isSubstituido;
    }

    public int contaOcorrencias(int valor){
        int ocorrencias = 0;

        for(int i = 0; i < nroElem; i++){
            if(vetor[i] == valor){
                ocorrencias++;
            }
        }

        return ocorrencias;
    }

    public boolean adicionaNoInicio(int valor){
        if(nroElem == vetor.length){
            System.out.println("Lista cheia!");
            return false;
        }

        int valorAuxiliar = vetor[0];
        for(int i = 0; i < nroElem; i++){
            if(i == 0){
                vetor[0] = valor;
            }else{
                int valorAuxilio2 = vetor[i];
                vetor[i] = valorAuxiliar;
                valorAuxiliar = valorAuxilio2;
            }
        }

        return true;
    }

}