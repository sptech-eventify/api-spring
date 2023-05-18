package eventify.api_spring.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "Estacionamento")
    private String descricao;

    public Servico(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Servico() {
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
}
