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
public class LogTarefaCriacaoDto {
    private Integer idTarefa;
    private Integer idUsuario;
    private Integer idFuncionario;
    private LocalDateTime dataCriacao;
    private String valor;
    private Integer idAcao;
}