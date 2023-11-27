package eventify.api_spring.dto.smartsync.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KanbanStatusDto {
    private Integer idSecao;
    private Integer tarefasEmAndamento;
    private Integer tarefasPendentes;
    private Integer tarefasRealizadas;
}