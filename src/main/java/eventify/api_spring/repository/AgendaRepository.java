package eventify.api_spring.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eventify.api_spring.domain.agenda.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    @Query("SELECT a FROM Agenda a WHERE a.buffet.id = :idBuffet AND DATE(a.data) = :data")
    Optional<Agenda> findByBuffetIdAndDataEquals(@Param("idBuffet") Integer idBuffet, @Param("data") LocalDate data);

    List<Agenda> findByBuffetId(Integer idBuffet);
}