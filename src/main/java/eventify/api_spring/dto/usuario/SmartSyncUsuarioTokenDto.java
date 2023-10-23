package eventify.api_spring.dto.usuario;

import eventify.api_spring.domain.usuario.NivelAcesso;

public record SmartSyncUsuarioTokenDto(Integer id, String nome, String email, String cpf, Integer tipoUsuario, NivelAcesso nivelAcesso, String token) {
}