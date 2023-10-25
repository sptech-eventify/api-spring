package eventify.api_spring.domain.smartsync;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileEvento {
    private Integer idEvento;
    private String nome;
    private String cpf;
    private String email;
    private Timestamp data;
    private Double preco;
    private String status;
    private Timestamp dataPedido;
}
