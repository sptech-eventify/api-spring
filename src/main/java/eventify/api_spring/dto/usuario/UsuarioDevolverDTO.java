package eventify.api_spring.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDevolverDto {
    private Integer id;
    private String nome;
    private String email;
    private String foto;
}