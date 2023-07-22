package eventify.api_spring.factory.usuario;

import eventify.api_spring.dto.usuario.UsuarioTokenDto;

public class UsuarioTokenDTOFactory {

    public static UsuarioTokenDto usuarioTokenDto(){
        return new UsuarioTokenDto(1, 1, "Gabriel Santos", "gabriel@santos.com", "sla", "foto");
    }
}
