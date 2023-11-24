package eventify.api_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eventify.api_spring.domain.smartsync.Acao;

public interface AcaoRepository extends JpaRepository<Acao, Integer> {
}