package eventify.api_spring.domain.endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "true")
    private boolean isValidado;

    @Schema(example = "R. Gualaxo")
    private String logradouro;

    @Schema(example = "285")
    private String numero;

    @Schema(example = "Aclimação")
    private String bairro;

    @Schema(example = "São Paulo")
    private String cidade;

    @Schema(example = "SP")
    private String uf;

    @Schema(example = "01533-010")
    private String cep;

    @Schema(example = "-23.574011")
    private Double latitude;

    @Schema(example = "46.6388203")
    private Double longitude;

    @Schema(example = "2023-05-20 17:49:12")
    private LocalDateTime dataCriacao;
}