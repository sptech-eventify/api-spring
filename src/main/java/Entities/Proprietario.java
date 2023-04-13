package api.eventify.backend.Models;

import javax.persistence.*;

@Entity
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
    @OneToOne
    private Usuario usuario;

    public Proprietario(Integer id, String cnpj, Usuario usuario) {
        this.id = id;
        this.cnpj = cnpj;
        this.usuario = usuario;
    }

    public Proprietario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
