package eventify.api_spring.mapper.usuario;

import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDTO;
import eventify.api_spring.dto.usuario.UsuarioDevolverDto;
import eventify.api_spring.dto.usuario.UsuarioTokenDto;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDevolverDto toDevolverDto(Usuario domain);

    public static Usuario of(UsuarioCadastrarDTO usuarioCadastrarDTO) {
        Usuario usuario = new Usuario();
        usuario.setIsAtivo(false);
        usuario.setIsBanido(false);
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