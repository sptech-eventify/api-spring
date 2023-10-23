package eventify.api_spring.domain.usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmartSyncUsuario {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private Integer tipoUsuario;
    private NivelAcesso nivelAcesso;
}