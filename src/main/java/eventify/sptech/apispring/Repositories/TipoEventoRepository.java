package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {
}
