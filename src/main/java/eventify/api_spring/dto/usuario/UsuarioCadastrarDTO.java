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

    public UsuarioCadastrarDTO() {
    }

    public UsuarioCadastrarDTO(String nome, String email, String senha, String cpf, Integer tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.tipoUsuario = tipoUsuario;
        this.isAtivo = true;
        this.isBanido = false;
    }

    private Boolean isAtivo = false;
    private Boolean isBanido = false;


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

    public Boolean getAtivo() {
        return isAtivo;
    }

    public void setAtivo(Boolean ativo) {
        isAtivo = ativo;
    }

    public Boolean getBanido() {
        return isBanido;
    }

    public void setBanido(Boolean banido) {
        isBanido = banido;
    }
}


