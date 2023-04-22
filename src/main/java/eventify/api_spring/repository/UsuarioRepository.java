package eventify.api_spring.repository;

import eventify.api_spring.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Interface que herda de JpaRepository e recebe a classe que será mapeada e o tipo do ID
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
