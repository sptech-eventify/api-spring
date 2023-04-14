package eventify.sptech.apispring.repository;

import eventify.sptech.apispring.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface que herda de JpaRepository e recebe a classe que será mapeada e o tipo do ID
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
