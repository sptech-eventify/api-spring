package eventify.api_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.smartsync.LogAcessoTarefa;
import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.Usuario;

public interface LogAcessoTarefaRepository extends JpaRepository<LogAcessoTarefa, Integer> {
    List<LogAcessoTarefa> findAllByFuncionario(Funcionario funcionario);
    List<LogAcessoTarefa> findAllByUsuario(Usuario usuario);
}