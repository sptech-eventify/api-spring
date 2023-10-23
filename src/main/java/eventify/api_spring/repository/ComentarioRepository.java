package eventify.api_spring.repository;

import eventify.api_spring.domain.smartsync.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository  extends JpaRepository<Comentario, Integer> {

}
