package eventify.api_spring.dto.usuario;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.service.usuario.dto.UsuarioTokenDto;

import java.time.LocalDateTime;

public class UsuarioMapper {
    public static Usuario of(UsuarioCadastrarDTO usuarioCadastrarDTO) {
        Usuario usuario = new Usuario();
        usuario.setAtivo(false);
        usuario.setBanido(false);
        usuario.setEmail(usuarioCadastrarDTO.getEmail());
        usuario.setTipoUsuario(usuarioCadastrarDTO.getTipoUsuario());
        usuario.setNome(usuarioCadastrarDTO.getNome());
        usuario.setCpf(usuarioCadastrarDTO.getCpf());
        usuario.setTipoUsuario(usuarioCadastrarDTO.getTipoUsuario());
        usuario.setSenha(usuarioCadastrarDTO.getSenha());
        usuario.setDataCriacao(LocalDateTime.now());
        return usuario;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto(usuario.getId(),
                usuario.getTipoUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                token,
                usuario.getFoto());
        return usuarioTokenDto;
    }
}