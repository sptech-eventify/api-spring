package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface que herda de JpaRepository e recebe a classe que será mapeada e o tipo do ID
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
