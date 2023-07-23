package eventify.api_spring.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UsuarioCadastrarDTO {
    @Schema(example = "Gabriel Santos")
    @NotBlank
    String nome;
    @NotBlank
    @Schema(example = "gabriel@santos.com")
    String email;
    @NotBlank
    @Schema(example = "123456")
    String senha;

    @Schema(example = "31509983015")
    @CPF
    String cpf;

    @Schema(example = "1")
    Integer tipoUsuario;
    
    private Boolean isAtivo = false;
    private Boolean isBanido = false;


    public UsuarioCadastrarDTO(String nome, String email, String senha, String cpf, Integer tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.tipoUsuario = tipoUsuario;
        this.isAtivo = true;
        this.isBanido = false;
    }
}