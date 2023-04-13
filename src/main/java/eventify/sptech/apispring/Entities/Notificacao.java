package eventify.sptech.apispring.Entities;

import jakarta.persistence.*;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String informacao;
    @ManyToOne
    private Proprietario proprietario;

    public Notificacao(Integer id, String informacao, Proprietario proprietario) {
        this.id = id;
        this.informacao = informacao;
        this.proprietario = proprietario;
    }

    public Notificacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInformacao() {
        return informacao;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
}
