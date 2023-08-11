package eventify.api_spring.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosFinanceiro {
    private Object mes;
    private Object qtdEventos;
    private Object faturamento;
}
