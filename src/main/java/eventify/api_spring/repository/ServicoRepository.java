package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eventify.api_spring.domain.buffet.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
}
