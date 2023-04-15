package eventify.api_spring.repository;

import eventify.api_spring.domain.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {
}
