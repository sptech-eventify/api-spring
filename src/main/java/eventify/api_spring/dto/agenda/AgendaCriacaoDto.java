package eventify.api_spring.dto.agenda;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AgendaCriacaoDto {
    private LocalDateTime data;
    private Integer idBuffet;
}
