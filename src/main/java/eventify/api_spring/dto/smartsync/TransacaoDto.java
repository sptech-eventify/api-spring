package eventify.api_spring.dto.smartsync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDto {
    private String pagante;
    private String cpf;
    private String email;
    private double valor;
    private Integer isPago;
    private String motivo;
    private String data;
}
