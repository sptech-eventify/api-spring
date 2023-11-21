package eventify.api_spring.dto.smartsync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExecutorDto {
    private Integer id;
    private String nome;
    private String urlFoto;
    private Integer tempoExecutado;
    private Integer idUsuario;
    private Integer idFuncionario;
}
