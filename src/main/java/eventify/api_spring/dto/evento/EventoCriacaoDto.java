package eventify.api_spring.dto.evento;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventoCriacaoDto {
    @FutureOrPresent
    private LocalDate data;

    private Double preco;
    private Boolean isFormularioDinamico;
    private Integer idBuffet;
    private Integer idUsuario; 
}
