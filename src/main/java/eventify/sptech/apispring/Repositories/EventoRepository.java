package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Buffet;
import eventify.sptech.apispring.Entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("select e from Evento e where e.buffet = :buffet")
    public List<Evento> findByBuffet(Buffet buffet);

}
