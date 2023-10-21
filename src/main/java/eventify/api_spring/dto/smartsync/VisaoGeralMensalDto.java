package eventify.api_spring.dto.smartsync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisaoGeralMensalDto {
    private List<InfoFinanceiroEventoDto> realizados;
    private List<InfoFinanceiroEventoDto> proximos;
}