package eventify.api_spring.repository;

import eventify.api_spring.domain.smartsync.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
    @Query("SELECT t FROM Tarefa t WHERE t.bucket.id = :id AND t.isVisivel = 1")
    List<Tarefa> findAllByBucketIdAndIsVisivelFalse(Integer id);
}
