package eventify.api_spring.repository;

import eventify.api_spring.domain.smartsync.ExecutorTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExecutorTarefaRepository extends JpaRepository<ExecutorTarefa, Integer> {

    @Query("SELECT e FROM ExecutorTarefa e WHERE e.funcionario.id = :id")
    List<ExecutorTarefa> findAllByFuncionarioId(Integer id);

}
