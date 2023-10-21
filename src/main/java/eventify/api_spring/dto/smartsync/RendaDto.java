package eventify.api_spring.dto.smartsync;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RendaDto {
    private String nome;
    private Timestamp data;
    private Double valor;
}