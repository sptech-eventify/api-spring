package eventify.api_spring.repository;

import eventify.api_spring.domain.smartsync.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComentarioRepository  extends JpaRepository<Comentario, Integer> {

}
