package eventify.api_spring.dto.evento;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoProximoDto {
    private String nome;
    private LocalDateTime data;
}
