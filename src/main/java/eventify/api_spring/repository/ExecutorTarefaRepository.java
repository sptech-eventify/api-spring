package eventify.api_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.smartsync.ExecutorTarefa;
import eventify.api_spring.domain.smartsync.Tarefa;

public interface ExecutorTarefaRepository extends JpaRepository<ExecutorTarefa, Integer> {
    List<ExecutorTarefa> findAllByFuncionarioId(Integer idFuncionario);

    List<ExecutorTarefa> findAllByTarefa(Tarefa tarefa);
}