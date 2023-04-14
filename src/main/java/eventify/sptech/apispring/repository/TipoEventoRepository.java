package eventify.sptech.apispring.repository;

import eventify.sptech.apispring.entity.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {
}
