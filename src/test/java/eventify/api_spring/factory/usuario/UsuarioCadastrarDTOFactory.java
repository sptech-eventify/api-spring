package eventify.api_spring.factory.usuario;

import eventify.api_spring.dto.usuario.UsuarioCadastrarDto;

public class UsuarioCadastrarDTOFactory {

    public static UsuarioCadastrarDto usuarioCadastrarDTO() {
        return new UsuarioCadastrarDto(
                "Gabriel Santos",
                "gabriel@santos.com",
                "123456",
                "31509983015",
                1
        );
    }

}
