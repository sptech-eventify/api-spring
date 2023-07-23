package eventify.api_spring.domain.endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

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
    @NotBlank
    private String logradouro;

    @Schema(example = "285")
    @Min(1)
    private Integer numero;

    @Schema(example = "Aclimação")
    @NotBlank
    private String bairro;

    @Schema(example = "São Paulo")
    @NotBlank
    private String cidade;

    @Schema(example = "SP")
    @NotBlank
    private String uf;

    @Schema(example = "01533-010")
    @NotBlank
    private String cep;

    @Schema(example = "-23.574011")
    private Double latitude;

    @Schema(example = "46.6388203")
    private Double longitude;

    @Schema(example = "2023-05-20 17:49:12")
    private LocalDate dataCriacao;
}