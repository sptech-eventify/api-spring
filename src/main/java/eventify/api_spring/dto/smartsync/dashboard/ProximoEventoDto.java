package eventify.api_spring.dto.smartsync.dashboard;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProximoEventoDto {
    private Integer idEvento;
    private String nomeCliente;
    private LocalDateTime dataEvento;
    private List<KanbanStatusDto> kanban;
}
