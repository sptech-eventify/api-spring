package api.eventify.backend.Models;

import javax.persistence.*;

@Entity
public class Contratante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cpf;
    @OneToOne
    private Usuario usuario;
    @ManyToOne
    private Endereco endereco;

    public Contratante(Integer id, String cpf, Usuario usuario, Endereco endereco) {
        this.id = id;
        this.cpf = cpf;
        this.usuario = usuario;
        this.endereco = endereco;
    }

    public Contratante() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
