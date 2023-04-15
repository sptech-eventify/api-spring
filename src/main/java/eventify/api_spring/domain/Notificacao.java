package eventify.api_spring.domain;
import jakarta.persistence.*;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    @ManyToOne
    private Usuario usuario;

    public Notificacao(Integer id, String descricao, Usuario usuario) {
        this.id = id;
        this.descricao = descricao;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
