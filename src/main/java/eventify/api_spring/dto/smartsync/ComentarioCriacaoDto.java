package eventify.api_spring.dto.smartsync;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioCriacaoDto {
    private String mensagem;
    private LocalDateTime dataCriacao;
    private Boolean isVisivel;
    private Integer idFuncionario;
    private Integer idUsuario;
    private Integer idTarefa;
}