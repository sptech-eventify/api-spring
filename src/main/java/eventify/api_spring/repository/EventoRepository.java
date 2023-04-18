package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("select e from Evento e where e.buffet = :buffet")
    public List<Evento> findByBuffet(Buffet buffet);

    @Query("select e from Evento e where e.data = :data")
    public List<Evento> findByData(LocalDate data);

    @Query("select e from Evento e where e.buffet.nome = :nome")
    Optional<Evento> findByBuffet(String nome);
}
