package eventify.api_spring.dto.agenda;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AgendaCriacaoDto {
    @NotNull
    @FutureOrPresent
    private LocalDateTime data;
    private Integer idBuffet;
}
