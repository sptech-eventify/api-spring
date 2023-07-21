package eventify.api_spring.api.assets;
import java.util.ArrayList;
import java.util.List;

public class Fila<T> {
    private T[] fila;
    private int tamanho;
    private int capacidade;

    public Fila(int capacidade) {
        fila = (T[]) new Object[capacidade];
        tamanho = 0;
        this.capacidade = capacidade;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == capacidade;
    }

    public void insert(T info) {
        if (!isFull()) {
            fila[tamanho] = info;
            tamanho++;
        } else {
            throw new IllegalStateException("Fila cheia!");
        }
    }

    public T peek() {
        if (!isEmpty()) {
            return fila[0];
        } else {
            throw new IllegalStateException("Fila vazia!");
        }
    }

    public T poll() {
        if (!isEmpty()) {
            T primeiroElemento = fila[0];
            for (int i = 0; i < tamanho - 1; i++) {
                fila[i] = fila[i + 1];
            }
            tamanho--;
            return primeiroElemento;
        } else {
            throw new IllegalStateException("Fila vazia!");
        }
    }

    public void exibe() {
        if (!isEmpty()) {
            for (int i = 0; i < tamanho; i++) {
                System.out.println(fila[i]);
            }
        } else {
            System.out.println("Fila vazia");
        }
    }

    public List<T> getValores() {
        if (!isEmpty()) {
            List<T> valores = new ArrayList<>();
            for (int i = 0; i < tamanho; i++) {
                valores.add(fila[i]);
            }
            return valores;
        } else {
            throw new IllegalStateException("Fila vazia!");
        }
    }
}
