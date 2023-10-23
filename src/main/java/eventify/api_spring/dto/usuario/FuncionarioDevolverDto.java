package eventify.api_spring.dto.usuario;

import eventify.api_spring.domain.usuario.NivelAcesso;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDevolverDto {
    Integer id;
    String nome;
    String cpf;
    String email;
    String telefone;
    NivelAcesso nivelAcesso;
    Double salario;
}
