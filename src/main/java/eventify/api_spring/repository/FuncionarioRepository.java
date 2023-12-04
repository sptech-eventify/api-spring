package eventify.api_spring.repository;

import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByEmail(String email);

    @Query("SELECT f FROM Funcionario f WHERE f.empregador = :usuario")
    List<Funcionario> findAllByEmpregador(@Param("usuario") Usuario usuario);
}
