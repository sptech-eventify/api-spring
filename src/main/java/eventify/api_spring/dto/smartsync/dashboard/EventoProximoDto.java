package eventify.api_spring.dto.smartsync.dashboard;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoProximoDto {
    private String cliente;
    private Integer id;
    private Timestamp data;
    private Integer status;
    private Integer tarefasRealizadas;
    private Integer tarefasPendentes;
    private Integer tarefasEmAndamento;
}