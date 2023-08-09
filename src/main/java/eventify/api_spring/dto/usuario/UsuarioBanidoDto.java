package eventify.api_spring.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioBanidoDto {
    private String nome;
    private String tipoUsuario;
    private String cpf;
}