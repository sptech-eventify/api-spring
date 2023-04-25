package eventify.api_spring.api.assets;

public class ListaEstaticaOrdenada extends ListaEstatica{
    public ListaEstaticaOrdenada(int tamanho) {
        super(tamanho);
    }

    @Override
    public void adiciona(int valor) {
        if (getNroElem() == getVetor().length) {
            System.out.println("Lista cheia!");
        } else {
            int i = 0;
            while (i < getNroElem() && valor > getVetor()[i]) {
                i++;
            }

            for (int j = getNroElem(); j > i; j--) {
                getVetor()[j] = getVetor()[j - 1];
            }

            getVetor()[i] = valor;
            setNroElem(getNroElem() + 1);
        }
    }
}