package eventify.api_spring.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import io.swagger.v3.oas.annotations.media.Schema;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}


