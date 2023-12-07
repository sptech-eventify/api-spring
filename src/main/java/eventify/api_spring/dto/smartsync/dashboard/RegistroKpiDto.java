package eventify.api_spring.dto.smartsync.dashboard;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroKpiDto {
    private Long ultimaSemana;
    private Long ultimosTresMeses;
    private Long total;
    private BigDecimal percentualContratantes;
    private BigDecimal percentualProprietarios;
}