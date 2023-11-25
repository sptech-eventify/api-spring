package eventify.api_spring.dto.smartsync;

import java.time.LocalDateTime;

import eventify.api_spring.domain.buffet.Servico;
import eventify.api_spring.domain.smartsync.Acao;
import eventify.api_spring.dto.usuario.FuncionarioDevolverDto;
import eventify.api_spring.dto.usuario.UsuarioDevolverDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogTarefaDto {
    private Integer id;
    private String valor;
    private LocalDateTime dataCriacao;
    private TarefaDto tarefa;
    private UsuarioDevolverDto usuario;
    private FuncionarioDevolverDto funcionario;
    private Acao acao;
    private Servico servico;
}
