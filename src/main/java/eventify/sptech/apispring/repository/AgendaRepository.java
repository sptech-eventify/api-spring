package eventify.sptech.apispring.repository;

import eventify.sptech.apispring.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
}
