package eventify.api_spring.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KpiEventifyUnificadoDto {
    private RendaMesAtualDto rendaMesAtual;
    private RendaMesAnteriorDto rendaMesAnterior;
    private Double rendaTotal;
}
