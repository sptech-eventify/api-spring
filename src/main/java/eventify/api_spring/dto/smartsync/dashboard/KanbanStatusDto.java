package eventify.api_spring.dto.smartsync.dashboard;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KanbanStatusDto {
    private Integer idEvento;
    private String cliente;
    private LocalDate dataEvento;
    private LocalDate dataEstimada;
    private Integer tarefasEmAndamento;
    private Integer tarefasPendentes;
    private Integer tarefasRealizadas;
    private Integer idServico;
}