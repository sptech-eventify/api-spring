package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventify.api_spring.domain.agenda.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
}