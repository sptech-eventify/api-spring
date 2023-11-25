package eventify.api_spring.mapper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eventify.api_spring.domain.smartsync.FlagLog;
import eventify.api_spring.domain.usuario.Funcionario;
import eventify.api_spring.domain.usuario.Usuario;

@Repository
public interface FlagLogRepository extends JpaRepository<FlagLog, Integer> {
    List<FlagLog> findAllByFuncionario(Funcionario funcionario);
    List<FlagLog> findAllByUsuario(Usuario usuario);

    @Query("SELECT fl FROM FlagLog fl WHERE fl.funcionario.id = :idFuncionario AND fl.tarefa.bucket.buffetServico = :idSecao")
    List<FlagLog> encontrarPorSecaoFuncionario(Integer idFuncionario, Integer idSecao);

    @Query("SELECT fl FROM FlagLog fl WHERE fl.usuario.id = :idUsuario AND fl.tarefa.bucket.buffetServico = :idSecao")
    List<FlagLog> encontrarPorSecaoUsuario(Integer idUsuario, Integer idSecao);
}
