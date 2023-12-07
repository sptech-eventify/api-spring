package eventify.api_spring.dto.smartsync.dashboard;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProprietarioKpiDto {
    private Integer buffetsInativos;
    private Integer conversaoReservaPorCem;
    private Integer fechamentoContratoPorCem;
    private List<CategoriaKpiDto> categorias;
}
