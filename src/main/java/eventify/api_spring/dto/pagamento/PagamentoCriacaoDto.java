package eventify.api_spring.dto.pagamento;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoCriacaoDto {
    private Boolean isPagoContrato;
    private LocalDate dataPagamento;
    private Boolean isPagoBuffet;
}
