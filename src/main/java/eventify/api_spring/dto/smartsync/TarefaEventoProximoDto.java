package eventify.api_spring.dto.smartsync;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TarefaEventoProximoDto {
    private Integer idTarefa;
    private String nomeContratante;
    private Timestamp dataEvento;
    private String nomeTarefa;
    private String descricaoTarefa;
    private List<String> responsaveisTarefa;
    private Timestamp dataTarefa;
}