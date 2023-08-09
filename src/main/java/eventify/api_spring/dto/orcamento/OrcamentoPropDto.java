package eventify.api_spring.dto.orcamento;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrcamentoPropDto {
    private Integer id;
    private String nomeContratante;
    private LocalDate data;
    private Double preco;
    private String status; 
}
