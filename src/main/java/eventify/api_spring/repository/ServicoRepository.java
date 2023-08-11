package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.buffet.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
}
