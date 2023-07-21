package eventify.api_spring.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioAdminDto(String nome, Integer tipoUsuario, Boolean isBanido, Boolean isAtivo, String cpf, LocalDateTime dataCriacao, Long qtdEventos, Long qtdOrcamentos) {
}

