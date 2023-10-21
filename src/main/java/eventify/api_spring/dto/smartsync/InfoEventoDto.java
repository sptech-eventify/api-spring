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
public class InfoEventoDto {
    private String nome;
    private String cpf;
    private String email;
    private Timestamp data;
    private String status;
    private Timestamp dataPedido;
}
