package eventify.api_spring.dto.usuario;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioCadastrarDto {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String telefone;
    private Double salario;
    private Integer diaPagamento;
    private Integer idEmpregador;
    private Integer idNivelAcesso;
}
