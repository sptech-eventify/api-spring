package eventify.api_spring.dto.smartsync;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogAcessoTarefaDto {
    private Integer id;
    private LocalDateTime dataCriacao;
    private TarefaDto tarefa;    
}