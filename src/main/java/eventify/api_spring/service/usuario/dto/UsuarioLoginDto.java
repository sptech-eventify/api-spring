package eventify.api_spring.service.usuario.dto;
import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioLoginDto {
    @Schema(example = "gabriel@santos.com")
    private String email;

    @Schema(example = "123456")
    private String senha;

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
}
