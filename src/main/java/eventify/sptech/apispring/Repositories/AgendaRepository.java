package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
}
