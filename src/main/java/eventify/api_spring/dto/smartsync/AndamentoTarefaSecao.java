package eventify.api_spring.dto.smartsync;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AndamentoTarefaSecao {
    private String nome;
    private Integer idEvento;
    private LocalDateTime dataEvento;
    private Integer quantidadeRealizadas;
    private Integer quantidadeTarefasPendentes;
    private Integer quantidadeTarefasEmAndamento;
}
