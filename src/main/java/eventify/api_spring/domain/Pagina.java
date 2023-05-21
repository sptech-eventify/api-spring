package eventify.api_spring.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class Pagina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String uri;
    @OneToMany(mappedBy = "pagina")
    private List<Acesso> acesso;

    public Pagina(Integer id, String nome, String uri, List<Acesso> acesso) {
        this.id = id;
        this.nome = nome;
        this.uri = uri;
        this.acesso = acesso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Acesso> pegarAcessos() {
        return acesso;
    }

    public void setAcesso(List<Acesso> acesso) {
        this.acesso = acesso;
    }
}
