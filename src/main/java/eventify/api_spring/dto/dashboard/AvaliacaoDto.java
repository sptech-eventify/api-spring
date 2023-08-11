package eventify.api_spring.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoDto {
    private Object nome;
    private Object nota;
    private Object avaliacao;
    private Object data;
}