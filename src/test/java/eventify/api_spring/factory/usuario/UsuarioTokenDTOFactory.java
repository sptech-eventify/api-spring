package eventify.api_spring.factory.usuario;

import eventify.api_spring.dto.usuario.UsuarioTokenDto;

public class UsuarioTokenDTOFactory {

    public static UsuarioTokenDto usuarioTokenDto(){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();
        usuarioTokenDto.setToken("sla");
        usuarioTokenDto.setEmail("gabriel@santos.com");
        usuarioTokenDto.setNome("Gabriel Santos");
        usuarioTokenDto.setUserId(1);

        return usuarioTokenDto;
    }
}
