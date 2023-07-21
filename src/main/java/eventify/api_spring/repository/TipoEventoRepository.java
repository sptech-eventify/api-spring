package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventify.api_spring.domain.buffet.TipoEvento;

@Repository
public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {
}
