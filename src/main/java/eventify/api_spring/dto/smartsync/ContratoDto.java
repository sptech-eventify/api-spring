package eventify.api_spring.dto.smartsync;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratoDto {
    private Integer id;
    private String nome;
    private Double preco;
    private Timestamp data;
    private Integer status;
    private String more;
}