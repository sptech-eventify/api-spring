package eventify.api_spring.mapper.usuario;

import eventify.api_spring.domain.usuario.NivelAcesso;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.usuario.SmartSyncUsuarioTokenDto;
import eventify.api_spring.dto.usuario.UsuarioCadastrarDto;
import eventify.api_spring.dto.usuario.UsuarioDevolverDto;
import eventify.api_spring.dto.usuario.UsuarioTokenDto;

import java.time.LocalDateTime;

public interface UsuarioMapper {
    public static Usuario of(UsuarioCadastrarDto usuarioCadastrarDto) {
        Usuario usuario = new Usuario();
        usuario.setIsAtivo(false);
        usuario.setIsBanido(false);
        usuario.setEmail(usuarioCadastrarDto.getEmail());
        usuario.setTipoUsuario(usuarioCadastrarDto.getTipoUsuario());
        usuario.setNome(usuarioCadastrarDto.getNome());
        usuario.setCpf(usuarioCadastrarDto.getCpf());
        usuario.setTipoUsuario(usuarioCadastrarDto.getTipoUsuario());
        usuario.setSenha(usuarioCadastrarDto.getSenha());
        usuario.setDataCriacao(LocalDateTime.now());

        return usuario;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto(usuario.getId(),
                usuario.getTipoUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                token,
                usuario.getImagem());

        return usuarioTokenDto;
    }

    public static SmartSyncUsuarioTokenDto ofUsuario(Usuario usuario, String token, NivelAcesso nivelAcesso) {
        SmartSyncUsuarioTokenDto usuarioTokenDto = new SmartSyncUsuarioTokenDto(usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getCpf(),
            usuario.getTipoUsuario(),
            nivelAcesso,
            token);
  
        return usuarioTokenDto;
    }

    public static UsuarioDevolverDto toDevolverDto(Usuario domain){
        UsuarioDevolverDto usuarioDevolverDto = new UsuarioDevolverDto();
        usuarioDevolverDto.setId(domain.getId());
        usuarioDevolverDto.setNome(domain.getNome());
        usuarioDevolverDto.setEmail(domain.getEmail());
        usuarioDevolverDto.setImagem(domain.getImagem());

        return usuarioDevolverDto;
    }
}