package eventify.api_spring.dto.usuario;

public record UsuarioTokenDto(Integer userId, Integer tipoUsuario, String nome, String email, String token, String foto) {
}
