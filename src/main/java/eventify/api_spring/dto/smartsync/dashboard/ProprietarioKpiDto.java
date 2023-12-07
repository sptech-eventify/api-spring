package eventify.api_spring.dto.smartsync.dashboard;

import java.util.List;

import eventify.api_spring.service.smartsync.ConversaoVisitasDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProprietarioKpiDto {
    private List<BuffetInativoDto> buffetsInativos;
    private List<ConversaoVisitasDto> buffetsVisitasEventos;
    private Long qtdEventosCancelados;
    private Long qtdEventosTotal;
    private List<CategoriaKpiDto> categorias;
}
