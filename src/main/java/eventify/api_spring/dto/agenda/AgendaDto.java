package eventify.api_spring.dto.agenda;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgendaDto {
    private Integer id;
    private LocalDateTime data;
    private Integer idBuffet;
}
