package eventify.api_spring.factory.usuario;

import eventify.api_spring.service.usuario.dto.UsuarioLoginDto;

public class UsuarioLoginDTOFactory {

    public static UsuarioLoginDto usuarioLoginDto(){
        final UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("gabriel@santos.com");
        usuarioLoginDto.setSenha("123456");

        return usuarioLoginDto;
    }
}
