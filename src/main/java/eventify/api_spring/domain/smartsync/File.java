package eventify.api_spring.domain.smartsync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String pagante;
    private String cpf;
    private String email;
    private Double valor;
    private Integer is_gasto;
    private String motivo;
    private String data;
}