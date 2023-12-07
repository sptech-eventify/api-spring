package eventify.api_spring.dto.smartsync.dashboard;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuffetInativoDto {
    private Integer id;
    private String nome;
    private Timestamp ultimaVisita;
}