package eventify.api_spring.dto.smartsync.dashboard;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelaKpis {
    private BigDecimal faturamentoTotal;
    private BigDecimal gastosTotal;
    private BigDecimal AvaliacaoMedia;
}
