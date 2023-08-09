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
public class EventoDto {
    private Integer id; 
    private String nome; 
    private LocalDate data; 
    private Double preco; 
    private Double nota; 
    private String descricao; 
    private String caminho; 
    private String status;
}