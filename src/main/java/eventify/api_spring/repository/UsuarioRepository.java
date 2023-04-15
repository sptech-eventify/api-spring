package eventify.api_spring.repository;

import eventify.api_spring.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface que herda de JpaRepository e recebe a classe que será mapeada e o tipo do ID
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
