package eventify.api_spring.service.usuario.dto;

public record UsuarioTokenDto(Integer userId, Integer tipoUsuario, String nome, String email, String token, String foto) {
}
