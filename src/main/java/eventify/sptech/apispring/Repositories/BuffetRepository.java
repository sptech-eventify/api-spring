package eventify.sptech.apispring.Repositories;

import eventify.sptech.apispring.Entities.Buffet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuffetRepository extends JpaRepository<Buffet, Integer> {

    @Query(value = "SELECT * " +
            "FROM buffet " +
            "INNER JOIN buffet_tipo_evento " +
            "ON buffet.id = buffet_tipo_evento.id_buffet " +
            "INNER JOIN tipo_evento " +
            "on tipo_evento.id = buffet_tipo_evento.id_tipo_evento", nativeQuery = true)
    public List<Buffet> findAllBuffet();

}
