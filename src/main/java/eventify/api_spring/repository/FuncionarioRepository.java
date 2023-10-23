package eventify.api_spring.repository;

import eventify.api_spring.domain.usuario.Funcionario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByEmail(String email);
}
