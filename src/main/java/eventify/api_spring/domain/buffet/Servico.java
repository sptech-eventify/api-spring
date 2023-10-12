package eventify.api_spring.domain.buffet;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "Estacionamento")
    private String descricao;

    @OneToMany(mappedBy = "servico")
    @JsonIgnore
    private Set<BuffetServico> buffetServicos;
}
