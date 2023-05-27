package eventify.api_spring.factory.usuario;

import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;

public class UsuarioCadastrarDTOFactory {

    public static UsuarioCadastrarDTO usuarioCadastrarDTO() {
        return new UsuarioCadastrarDTO(
                "Gabriel Santos",
                "gabriel@santos.com",
                "123456",
                "31509983015",
                1
        );
    }

}
