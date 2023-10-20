package eventify.api_spring.repository;

import eventify.api_spring.domain.usuario.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
