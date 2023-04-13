package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
}
