package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.buffet.TipoEvento;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Integer> {
}
