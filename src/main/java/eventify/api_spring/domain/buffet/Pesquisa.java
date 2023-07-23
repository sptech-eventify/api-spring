package eventify.api_spring.domain.buffet;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pesquisa {
    @Schema(example = "Buffet")
    private String nome;

    @Schema(example = "1 a 5 anos")
    private List<String> faixaEtaria;

    @Schema(example = "100")
    private Integer tamanho;

    @Schema(example = "300")
    private Integer qtdPessoas;

    @Schema(example = "Casamento")
    private List<String> tipoEvento;

    @Schema(example = "1000.00")
    private Double orcMin;

    @Schema(example = "2000.00")
    private Double orcMax;

    @Schema(example = "2023-05-14 10:30:00")
    private LocalDate dataEvento;

    @Schema(example = "Decoração")
    private List<String> servico;

    @Schema(example = "-23.567890")
    private Double latitude;

    @Schema(example = "-46.789012")
    private Double longitude;
}
