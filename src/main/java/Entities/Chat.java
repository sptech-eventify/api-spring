package api.eventify.backend.Models;

import javax.persistence.*;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean isApagado;
    @OneToOne
    private Contratante contratante;
    @OneToOne
    private Proprietario proprietario;
    @OneToMany
    private List<Mensagem> mensagems;

    public Chat(Integer id, boolean isApagado, Contratante contratante, Proprietario proprietario) {
        this.id = id;
        this.isApagado = isApagado;
        this.contratante = contratante;
        this.proprietario = proprietario;
    }

    public Chat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isApagado() {
        return isApagado;
    }

    public void setApagado(boolean apagado) {
        isApagado = apagado;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
}
