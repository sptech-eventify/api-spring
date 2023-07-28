package eventify.api_spring.dto.evento;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoOrcamentoDto {
    private String nome;
    private LocalDate data;
    private Object status;
}
