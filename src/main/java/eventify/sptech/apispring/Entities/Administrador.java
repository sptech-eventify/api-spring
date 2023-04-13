package eventify.sptech.apispring.Entities;

import jakarta.persistence.*;

@Entity
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @OneToOne
    private Usuario usuario;

    public Administrador(Integer id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public Administrador() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
