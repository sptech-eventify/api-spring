package eventify.api_spring.repository;

import eventify.api_spring.domain.Usuario;
import eventify.api_spring.dto.BuffetInfoDto;
import eventify.api_spring.dto.usuario.BuffetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

// Interface que herda de JpaRepository e recebe a classe que será mapeada e o tipo do ID
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    @Validated
    @Query("SELECT new eventify.api_spring.dto.BuffetInfoDto(b.id, b.nome) FROM Buffet b WHERE b.usuario = :id")
    List<BuffetInfoDto> listarProprietariosBuffetQuantidade(Usuario id);
}
