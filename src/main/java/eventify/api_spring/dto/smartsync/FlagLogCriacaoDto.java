package eventify.api_spring.dto.smartsync;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlagLogCriacaoDto {
    private Integer idTarefa;
    private LocalDateTime dataCriacao;
    private Integer idFuncionario;
    private Integer idUsuario;
}
