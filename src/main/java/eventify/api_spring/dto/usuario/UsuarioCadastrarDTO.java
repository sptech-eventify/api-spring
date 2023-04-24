package eventify.api_spring.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioCadastrarDTO {
    @Schema(example = "Gabriel Santos")
    String nome;

    @Schema(example = "gabriel@santos.com")
    String email;

    @Schema(example = "123456")
    String senha;

    @Schema(example = "49308791811")
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


