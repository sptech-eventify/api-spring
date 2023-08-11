package eventify.api_spring.dto.usuario;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDto {
    @Schema(example = "gabriel@santos.com")
    private String email;

    @Schema(example = "123456")
    private String senha;
}
