package eventify.api_spring.dto.smartsync.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroKpiDto {
    private Integer ultimaSemana;
    private Integer ultimosTresMeses;
    private Integer total;
    private Integer percentualContratantes;
    private Integer percentualProprietarios;
}