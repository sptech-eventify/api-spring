package eventify.api_spring.repository;

import eventify.api_spring.domain.smartsync.Comentario;
import eventify.api_spring.domain.smartsync.Tarefa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository  extends JpaRepository<Comentario, Integer> {
    List<Comentario> findAllByTarefaAndIsVisivel(Tarefa tarefa, Boolean isVisivel);
}
