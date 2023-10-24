package eventify.api_spring.dto.smartsync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KpiUnificadoDto {
    private Integer qtdEventos;
    private Double media;
    private Double renda;
    private Double gasto;
}
