package eventify.api_spring.dto.evento;

import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoProximoDto {
    private Integer id;
    private String nome;
    private String diaSemana;
    private String data;
}
