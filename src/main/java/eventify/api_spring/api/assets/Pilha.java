package eventify.api_spring.api.assets;
import java.util.ArrayList;
import java.util.List;

public class Pilha<T> {
    private T[] pilha;
    private int topo;

    public Pilha(int capacidade) {
        pilha = (T[]) new Object[capacidade];
        topo = -1;
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == pilha.length - 1;
    }

    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
        } else {
            throw new IllegalStateException("Pilha cheia!");
        }
    }

    public T pop() {
        if (!isEmpty()) {
            return pilha[topo--];
        } else {
            return null;
        }
    }

    public T peek() {
        if (!isEmpty()) {
            return pilha[topo];
        } else {
            return null;
        }
    }

    public void exibe() {
        if (!isEmpty()) {
            for (int i = topo; i >= 0; i--) {
                System.out.println(pilha[i]);
            }
        } else {
            throw new IllegalStateException("Pilha vazia!");
        }
    }

    public String exibeNaLinha() {
        StringBuilder linha = new StringBuilder();

        if (!isEmpty()) {
            for (int i = topo; i >= 0; i--) {
                linha.append(pilha[i]);
            }
        } else {
            throw new IllegalStateException("Pilha vazia!");
        }

        return linha.toString();
    }

    public int getTopo() {
        return topo;
    }

    public boolean ehPalindromo() {
        int i = 0;
        int j = topo;

        while (i < j) {
            T compara1 = pilha[i];
            T compara2 = pilha[j];

            if ((compara1 instanceof String) && (compara2 instanceof String)) {
                compara1 = (T) ((String) compara1).toUpperCase();
                compara2 = (T) ((String) compara2).toUpperCase();
            } else if ((compara1 instanceof String) || (compara2 instanceof String)) {
                return false;
            }

            if (compara1.equals(compara2)) {
                i++;
                j--;
            } else {
                return false;
            }
        }

        return true;
    }

    public List<T> getValores() {
        List<T> valores = new ArrayList<>();

        if (!isEmpty()) {
            for (int i = topo; i >= 0; i--) {
                valores.add(pilha[i]);
            }
        } else {
            throw new IllegalStateException("Pilha vazia!");
        }

        return valores;
    }

}