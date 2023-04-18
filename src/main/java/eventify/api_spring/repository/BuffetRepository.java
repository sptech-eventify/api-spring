package eventify.api_spring.repository;

import eventify.api_spring.domain.Buffet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BuffetRepository extends JpaRepository<Buffet, Integer> {

    @Query(value = "SELECT * " +
            "FROM buffet " +
            "INNER JOIN buffet_tipo_evento " +
            "ON buffet.id = buffet_tipo_evento.id_buffet " +
            "INNER JOIN tipo_evento " +
            "on tipo_evento.id = buffet_tipo_evento.id_tipo_evento " +
            "INNER JOIN usuario on buffet.id_usuario = usuario.id", nativeQuery = true)
    public List<Buffet> findAllBuffet();

}
