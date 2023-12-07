package eventify.api_spring.dto.smartsync.dashboard;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormularioDinamicoDto {
    private Integer precisaoFormulario;
    private Integer utilizacaoFormulario;
    private List<UtlizacaoFormularioMensalDto> utilizacaoFormularioMensal;
}
