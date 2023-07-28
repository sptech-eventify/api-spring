package eventify.api_spring.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eventify.api_spring.domain.agenda.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    @Query("SELECT a FROM Agenda a WHERE a.buffet.id = :idBuffet AND DATE(a.data) = :data")
    Optional<Agenda> findByBuffetIdAndDataEquals(@Param("idBuffet") Integer idBuffet, @Param("data") LocalDate data);

    // Agenda findByBuffetIdAndDataEquals(Integer idBuffet, LocalDateTime data);
}