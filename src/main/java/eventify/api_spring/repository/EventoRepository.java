package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import eventify.api_spring.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("select e from Evento e where e.buffet = :buffet")
    public List<Evento> findByBuffet(Buffet buffet);

    @Query("select e from Evento e where e.buffet.nome = :nome")
    Optional<Evento> findByBuffet(String nome);

    @Query("select e.data from Evento e where e.buffet = :buffet")
    public List<LocalDate> findAllDataByBuffet(Buffet buffet);

    //    public List<Evento> findEventoByEqualsEvento(Evento evento);
}

