package eventify.api_spring.domain.buffet;
import jakarta.persistence.*;

import java.time.LocalDate;

import eventify.api_spring.domain.usuario.Usuario;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private LocalDate dataCriacao;

    @ManyToOne
    private Usuario usuario;

    public Notificacao(Integer id, String descricao, Usuario usuario, LocalDate dataCriacao) {
        this.id = id;
        this.descricao = descricao;
        this.usuario = usuario;
        this.dataCriacao = dataCriacao;
    }

    public Notificacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario usuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}